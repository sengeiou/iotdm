package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class SessionProcessor {


    private QueueSendServer queueSendService;

    public void doProcessDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        queueSendService.sendSessionData(deviceSessionEvent);
    }

    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }
}
