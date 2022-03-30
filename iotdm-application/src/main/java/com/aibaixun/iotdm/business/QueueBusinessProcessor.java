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

    private QueueSendServer queueSendService;

    public void processProperty(PostPropertyBusinessMsg propertyBusinessMsg) {
        queueSendService.sendPropertyTsData(propertyBusinessMsg);
        String deviceId = propertyBusinessMsg.getMetaData().getDeviceId();
        logP2P(deviceId, propertyBusinessMsg.getTsData());
    }

    public void processMessage(MessageBusinessMsg messageBusinessMsg) {
        queueSendService.sendMessageTsData(messageBusinessMsg);
        String deviceId = messageBusinessMsg.getMetaData().getDeviceId();
        logP2P(deviceId, messageBusinessMsg.getMessage());
    }

    public void processSessionData(DeviceSessionEvent deviceSessionEvent) {
        queueSendService.sendSessionData(deviceSessionEvent);
        String deviceId = deviceSessionEvent.getDeviceId();
        logP2P(deviceId, deviceSessionEvent);
    }

    public void processEntityChangeData(EntityChangeEvent entityChangeEvent) {
        queueSendService.sendEntityChangeData(entityChangeEvent);
    }


    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }
}
