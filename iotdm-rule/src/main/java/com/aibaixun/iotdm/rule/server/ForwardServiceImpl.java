package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.scheduler.RuleExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Service
public class ForwardServiceImpl implements ForwardService{


    /**
     * 转发执行器
     */
    private RuleExecutorService ruleExecutorService;





    @Override
    public void forwardPropertyReport(PostPropertyBusinessMsg propertyMessage, List<ForwardRuleInfo> forwardRuleInfos) {

    }

    @Override
    public void forwardMessageReport(MessageBusinessMsg message, List<ForwardRuleInfo> forwardRuleInfos) {

    }

    @Override
    public void forwardSessionReport(DeviceSessionEvent sessionEvent, List<ForwardRuleInfo> forwardRuleInfos) {

    }

    @Override
    public void forwardEntityReport(EntityChangeEvent entityChangeEvent, List<ForwardRuleInfo> forwardRuleInfos) {

    }

    @Autowired
    public void setRuleExecutorService(RuleExecutorService ruleExecutorService) {
        this.ruleExecutorService = ruleExecutorService;
    }
}
