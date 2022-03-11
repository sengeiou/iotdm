package com.aibaixun.iotdm.queue;

import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.event.DeviceSessionEvent;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface QueueSendService {

    /**
     * 发送 session 数据
     * @param deviceSessionEvent deviceSessionEvent
     */
    void sendSessionData(DeviceSessionEvent deviceSessionEvent);

    /**
     * 发送 属性 数据
     * @param postPropertyBusinessMsg postPropertyBusinessMsg
     */
    void sendPropertyTsData(PostPropertyBusinessMsg postPropertyBusinessMsg);


    /**
     * 发送 消息 数据
     * @param messageBusinessMsg messageBusinessMsg
     */
    void sendMessageTsData(MessageBusinessMsg messageBusinessMsg);
}
