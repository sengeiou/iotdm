package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.msg.TargetResourceInfo;
import com.aibaixun.iotdm.rule.QueueReceiveServiceImpl;
import com.aibaixun.iotdm.rule.send.SendService;
import com.aibaixun.iotdm.scheduler.RuleExecutorService;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 转发服务类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Service
public class ForwardServiceImpl implements ForwardService{


    private final Logger log = LoggerFactory.getLogger(QueueReceiveServiceImpl.class);

    /**
     * 转发执行器
     */
    private RuleExecutorService ruleExecutorService;


    @Override
    public <T> void sendMessage(T message, List<ForwardRuleInfo> forwardRuleInfos){
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return ;
        }
        List<TargetResourceInfo> allForwardTargetInfo = getAllForwardTargetInfo(forwardRuleInfos);
        if (CollectionUtils.isEmpty(allForwardTargetInfo)){
            return ;
        }
        for (TargetResourceInfo targetResourceInfo : allForwardTargetInfo) {
            log.info("ForwardServiceImpl.sendMessage >> ruleLabel:{},resourceLabel:{}",targetResourceInfo.getRuleLabel(),targetResourceInfo.getResourceLabel());
            doSendMessage(message,targetResourceInfo.getResourceType(),targetResourceInfo.getResourceConfig(), targetResourceInfo.getTargetConfig());
        }
    }

    /**
     * 默认发送方法
     * @param message 消息
     * @param resourceType 资源类型
     * @param resourceConfig 资源配置
     * @param targetConfig 发送目标配置
     * @param <T> 消息类型
     */
    private   <T> void  doSendMessage(T message, ResourceType resourceType, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig){
        SendService sendService = SendService.SEND_SERVICE_MAP.get(resourceType);
        if (Objects.nonNull(sendService)){
            ruleExecutorService.executeAsync(() -> {
                sendService.doSendMessage(message, resourceConfig, targetConfig);
                return true;
            });
        }
    }

    /**
     * 获取转发目标信息
     * @param forwardRuleInfos 转发规则
     * @return 转发目标信息
     */
    private List<TargetResourceInfo> getAllForwardTargetInfo(List<ForwardRuleInfo> forwardRuleInfos){
        List<TargetResourceInfo> targetResourceInfos = new ArrayList<>();
        for (ForwardRuleInfo forwardRuleInfo : forwardRuleInfos) {
            targetResourceInfos.addAll(forwardRuleInfo.getTargetResourceInfos());
        }
        return targetResourceInfos;
    }




    @Autowired
    public void setRuleExecutorService(RuleExecutorService ruleExecutorService) {
        this.ruleExecutorService = ruleExecutorService;
    }
}
