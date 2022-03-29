package com.aibaixun.iotdm.rule.server;


import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.msg.TargetResourceInfo;

import java.util.List;

/**
 * 转发服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public interface ForwardService {

    /**
     * 转发消息
     * @param message 消息
     * @param forwardRuleInfos 转发规则
     * @param <T> 消息类型
     */
    <T> void sendMessage(T message,List<ForwardRuleInfo> forwardRuleInfos);


    /**
     * 转发消息 前置处理
     * @param forwardRuleInfos 转发规则
     * @return    List<TargetResourceInfo> 转发信息
     */
    List<TargetResourceInfo>  beforeSendMessage(List<ForwardRuleInfo> forwardRuleInfos);


    /**
     * 转发消息 异常处理
     * @param e 异常信息
     */
    void  caughtSendMessage(Exception e);

    /**
     * 后置处理
     * @param message 消息
     * @param <T> 消息类型
     */
    <T>void  afterSendMessage(T message);
}
