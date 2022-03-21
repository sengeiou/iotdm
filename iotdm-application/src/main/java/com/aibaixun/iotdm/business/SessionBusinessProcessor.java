package com.aibaixun.iotdm.business;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
import com.aibaixun.iotdm.server.DeviceLogProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class SessionBusinessProcessor {


    private QueueSendServer queueSendService;

    private DeviceLogProcessor deviceLogProcessor;


    public void doProcessDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        deviceLogProcessor.doDevice2PlatformLog(deviceSessionEvent.getDeviceId(), BusinessStep.DEVICE_SESSION, JsonUtil.toJSONString(deviceSessionEvent),true);
        queueSendService.sendSessionData(deviceSessionEvent);
    }

    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }

    @Autowired
    public void setDeviceLogProcessor(DeviceLogProcessor deviceLogProcessor) {
        this.deviceLogProcessor = deviceLogProcessor;
    }
}
