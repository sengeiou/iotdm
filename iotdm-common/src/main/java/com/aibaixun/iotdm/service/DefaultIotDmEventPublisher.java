package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.DeviceMessageUpEvent;
import com.aibaixun.iotdm.event.DevicePropertyUpEvent;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.msg.SessionEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 事件发布者
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Service
public class DefaultIotDmEventPublisher implements IotDmEventPublisher {


    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishDeviceSessionEvent(String productId, String deviceId, SessionEventType sessionEventType) {
        applicationEventPublisher.publishEvent(new DeviceSessionEvent(deviceId,productId,sessionEventType));
    }


    @Override
    public void publishPropertyUpEvent(String productId, String deviceId, DataFormat dataFormat, String payload) {
        applicationEventPublisher.publishEvent(new DevicePropertyUpEvent(deviceId,productId,dataFormat,payload));
    }

    @Override
    public void publishMessageUpEvent(String productId, String deviceId, String payload) {
        applicationEventPublisher.publishEvent(new DeviceMessageUpEvent(deviceId,productId,payload));
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
