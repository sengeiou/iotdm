package com.aibaixun.iotdm.rule.server;


import com.aibaixun.iotdm.msg.ForwardRuleInfo;

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

}
