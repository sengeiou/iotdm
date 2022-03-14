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


    <T> void sendMessage(T message,List<ForwardRuleInfo> forwardRuleInfos);

}
