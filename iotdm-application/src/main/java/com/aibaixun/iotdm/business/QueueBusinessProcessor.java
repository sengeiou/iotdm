package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  队列处理类
 * @author Wang Xiao
 * @date 2022/3/30
 */
@Component
public class QueueBusinessProcessor extends AbstractBusinessProcessor {

    private QueueSendServer queueSendServer;

    public void processProperty2Mq(PostPropertyBusinessMsg propertyBusinessMsg) {
        queueSendServer.sendPropertyTsData(propertyBusinessMsg);
        String deviceId = propertyBusinessMsg.getMetaData().getDeviceId();
        logP2P(deviceId, propertyBusinessMsg.getTsData());
    }

    public void processMessage2Mq(MessageBusinessMsg messageBusinessMsg) {
        queueSendServer.sendMessageTsData(messageBusinessMsg);
        String deviceId = messageBusinessMsg.getMetaData().getDeviceId();
        logP2P(deviceId, messageBusinessMsg.getMessage());
    }

    public void processSessionData2Mq(DeviceSessionEvent deviceSessionEvent) {
        queueSendServer.sendSessionData(deviceSessionEvent);
        String deviceId = deviceSessionEvent.getDeviceId();
        logP2P(deviceId, deviceSessionEvent);
    }

    public void processEntityChangeData2Mq(EntityChangeEvent entityChangeEvent) {
        queueSendServer.sendEntityChangeData(entityChangeEvent);
    }


    @Autowired
    public void setQueueSendServer(QueueSendServer queueSendServer) {
        this.queueSendServer = queueSendServer;
    }
}
