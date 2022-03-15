package com.aibaixun.iotdm.rule;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.enums.SubjectEvent;
import com.aibaixun.iotdm.enums.SubjectResource;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.queue.IotDmSink;
import com.aibaixun.iotdm.queue.QueueReceiveService;
import com.aibaixun.iotdm.rule.server.ForwardService;
import com.aibaixun.iotdm.rule.server.RuleServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Service
@EnableBinding(value = { IotDmSink.class })
public class QueueReceiveServiceImpl implements QueueReceiveService {

    private final Logger log = LoggerFactory.getLogger(QueueReceiveServiceImpl.class);


    private RuleServer ruleService;

    private ForwardService forwardService;

    @Override
    @StreamListener(IotDmSink.INPUT_PROPERTY_TS_DATA)
    public <T> void receivePropertyTsData(GenericMessage<T> tsData) {
        log.info("QueueReceiveService receivePropertyTsData:{}",tsData);
        try {
            PostPropertyBusinessMsg propertyTsData = JsonUtil.toObject(tsData.getPayload().toString(),PostPropertyBusinessMsg.class) ;
            String productId = propertyTsData.getMetaData().getProductId();
            List<ForwardRuleInfo> forwardRule = getForwardRule(productId);
            List<ForwardRuleInfo> forwardRuleInfos = matchForwardRule(SubjectResource.DEVICE_PROPERTY, SubjectEvent.DEVICE_PROPERTY_REPORT, forwardRule);
            forwardService.sendMessage(propertyTsData,forwardRuleInfos);
        }catch (Exception e){
            log.info("QueueReceiveService receivePropertyTsData,error is:{}",e.getMessage());
        }

    }



    @Override
    @StreamListener(IotDmSink.INPUT_MESSAGE_TS_DATA)
    public <T> void receiveMessageTsData(GenericMessage<T> tsData) {
        log.info("QueueReceiveService receiveMessageTsData:{}",tsData);
        try {
            MessageBusinessMsg messageTsData = JsonUtil.toObject(tsData.getPayload().toString(),MessageBusinessMsg.class);
            String productId = messageTsData.getMetaData().getProductId();
            List<ForwardRuleInfo> forwardRule = getForwardRule(productId);
            List<ForwardRuleInfo> forwardRuleInfos = matchForwardRule(SubjectResource.DEVICE_MESSAGE, SubjectEvent.DEVICE_MESSAGE_REPORT, forwardRule);
            forwardService.sendMessage(tsData,forwardRuleInfos);
        }catch (Exception e){
            log.info("QueueReceiveService receiveMessageTsData,error is:{}",e.getMessage());
        }

    }

    @Override
    @StreamListener(IotDmSink.INPUT_SESSION_DATA)
    public <T> void receiveSessionData(GenericMessage<T> sessionData) {
        log.info("QueueReceiveService receiveSessionData:{}",sessionData);
        try {
            DeviceSessionEvent sessionEventData =  JsonUtil.toObject(sessionData.getPayload().toString(),DeviceSessionEvent.class);
            String productId = sessionEventData.getProductId();
            List<ForwardRuleInfo> forwardRule = getForwardRule(productId);
            List<ForwardRuleInfo> forwardRuleInfos = matchForwardRule(SubjectResource.DEVICE_STATUS, SubjectEvent.DEVICE_STATUS_UPDATE, forwardRule);
            forwardService.sendMessage(sessionData,forwardRuleInfos);
        }catch (Exception e){
            log.info("QueueReceiveService receiveSessionData,error is:{}",e.getMessage());
        }

    }


    @Override
    @StreamListener(IotDmSink.INPUT_ENTITY_DATA)
    public <T> void receiveEntityData(GenericMessage<T> entityData) {
        log.info("QueueReceiveService receiveEntityData:{}",entityData);
        try {
            EntityChangeEvent entityChangeEvent = JsonUtil.toObject(entityData.getPayload().toString(),EntityChangeEvent.class);
            List<ForwardRuleInfo> forwardRule = ruleService.queryForwardRule(entityChangeEvent.getTenantId());
            List<ForwardRuleInfo> forwardRuleInfos = matchForwardRule(entityChangeEvent.getSubjectResource(), entityChangeEvent.getSubjectEvent(), forwardRule);
            forwardService.sendMessage(entityData,forwardRuleInfos);
        }catch (Exception e){
            log.info("QueueReceiveService receiveSessionData,error is:{}",e.getMessage());
        }
    }


    /**
     * 查询 转发规则
     * @param productId 产品id
     * @return 转发规则
     */
    private List<ForwardRuleInfo> getForwardRule(String productId) {
        String currentProductTenantId = ruleService.getCurrentProductTenantId(productId);
        log.info("QueueReceiveServiceImpl.getForwardRule >> get ForwardInfo from tenantId:{}",currentProductTenantId);
        return ruleService.queryForwardRule(currentProductTenantId);
    }

    /**
     * 匹配转发信息
     * @param subjectResource 数据来源
     * @param subjectEvent 触发事件
     * @param forwardRuleInfos 转发规则信息
     * @return 匹配到的转发规则
     */
    private List<ForwardRuleInfo> matchForwardRule (SubjectResource subjectResource, SubjectEvent subjectEvent,List<ForwardRuleInfo> forwardRuleInfos) {
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return Collections.emptyList();
        }
        return forwardRuleInfos.stream().filter(e -> subjectResource.equals(e.getSubjectResource()) && subjectEvent.equals(e.getSubjectEvent())).collect(Collectors.toList());
    }



    @Autowired
    public void setRuleService(RuleServer ruleService) {
        this.ruleService = ruleService;
    }


    @Autowired
    public void setForwardService(ForwardService forwardService) {
        this.forwardService = forwardService;
    }
}
