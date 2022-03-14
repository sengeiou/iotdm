package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;

import java.util.List;

/**
 * 转发服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public interface ForwardService {

    /**
     * 转发属性数据
     * @param propertyMessage 属性数据
     * @param forwardRuleInfos 已经匹配成功的转发规则
     */
    void  forwardPropertyReport (PostPropertyBusinessMsg propertyMessage, List<ForwardRuleInfo> forwardRuleInfos);



    /**
     * 转发消息数据
     * @param message 消息数据
     * @param forwardRuleInfos 已经匹配成功的转发规则
     */
    void forwardMessageReport(MessageBusinessMsg message,List<ForwardRuleInfo> forwardRuleInfos);


    /**
     * 转发设备事件
     * @param sessionEvent 设备事件
     * @param forwardRuleInfos 已经匹配成功的转发规则
     */
    void forwardSessionReport(DeviceSessionEvent sessionEvent,List<ForwardRuleInfo> forwardRuleInfos);



    /**
     * 转发实体 更改
     * @param entityChangeEvent 实体更改
     * @param forwardRuleInfos 已经匹配成功的转发规则
     */
    void forwardEntityReport(EntityChangeEvent entityChangeEvent,List<ForwardRuleInfo> forwardRuleInfos);

}
