package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.msg.TargetResourceInfo;
import com.aibaixun.iotdm.rule.QueueReceiveServiceImpl;
import com.aibaixun.iotdm.scheduler.RuleExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
    public void forwardPropertyReport(PostPropertyBusinessMsg propertyMessage, List<ForwardRuleInfo> forwardRuleInfos) {
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return;
        }
        List<TargetResourceInfo> allForwardTargetInfo = getAllForwardTargetInfo(forwardRuleInfos);


    }

    @Override
    public void forwardMessageReport(MessageBusinessMsg message, List<ForwardRuleInfo> forwardRuleInfos) {
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return;
        }
        List<TargetResourceInfo> allForwardTargetInfo = getAllForwardTargetInfo(forwardRuleInfos);


    }

    @Override
    public void forwardSessionReport(DeviceSessionEvent sessionEvent, List<ForwardRuleInfo> forwardRuleInfos) {
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return;
        }
        List<TargetResourceInfo> allForwardTargetInfo = getAllForwardTargetInfo(forwardRuleInfos);


    }

    @Override
    public void forwardEntityReport(EntityChangeEvent entityChangeEvent, List<ForwardRuleInfo> forwardRuleInfos) {
        if (CollectionUtils.isEmpty(forwardRuleInfos)){
            return;
        }
        List<TargetResourceInfo> allForwardTargetInfo = getAllForwardTargetInfo(forwardRuleInfos);


    }

    @Autowired
    public void setRuleExecutorService(RuleExecutorService ruleExecutorService) {
        this.ruleExecutorService = ruleExecutorService;
    }


    private List<TargetResourceInfo> getAllForwardTargetInfo(List<ForwardRuleInfo> forwardRuleInfos){
        List<TargetResourceInfo> targetResourceInfos = new ArrayList<>();
        for (ForwardRuleInfo forwardRuleInfo : forwardRuleInfos) {
            targetResourceInfos.addAll(forwardRuleInfo.getTargetResourceInfos());
        }
        return targetResourceInfos;
    }
}
