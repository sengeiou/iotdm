package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.server.DeviceLogProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象业务处理类 只记录日志
 * @author Wang Xiao
 * @date 2022/3/30
 */
public abstract class AbstractBusinessProcessor {

    private DeviceLogProcessor deviceLogProcessor;

    protected final Logger logger = LoggerFactory.getLogger(AbstractBusinessProcessor.class);

    @Autowired
    public void setDeviceLogProcessor(DeviceLogProcessor deviceLogProcessor) {
        this.deviceLogProcessor = deviceLogProcessor;
    }

    protected void logD2P(String deviceId, BusinessStep step,String message,boolean status){
        deviceLogProcessor.doDevice2PlatformLog(deviceId, step, message,status);
    }

    protected void logP2D(String deviceId, BusinessStep step,String message,boolean status){
        deviceLogProcessor.doPlatform2DeviceLLog(deviceId, step, message,status);
    }

    protected void logP2P(String deviceId, Object message){
        deviceLogProcessor.doPlatform2PlatformLog(deviceId, BusinessStep.PLATFORM_SEND_DATA2MQ, String.valueOf(message), true);
    }
}
