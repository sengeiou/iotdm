package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.DeviceSessionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * session 会话处理类
 * @author Wang Xiao
 * @date 2022/3/30
 */
@Component
public class SessionBusinessProcessor extends AbstractBusinessProcessor {


    private QueueBusinessProcessor queueBusinessProcessor;

    public void doProcessDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        queueBusinessProcessor.processSessionData(deviceSessionEvent);
    }

    @Autowired
    public void setQueueBusinessProcessor(QueueBusinessProcessor queueBusinessProcessor) {
        this.queueBusinessProcessor = queueBusinessProcessor;
    }
}
