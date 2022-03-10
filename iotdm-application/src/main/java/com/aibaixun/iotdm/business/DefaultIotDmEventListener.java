package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.DeviceMessageUpEvent;
import com.aibaixun.iotdm.event.DevicePropertyUpEvent;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Component
public class DefaultIotDmEventListener implements IotDmEventListener{

    private final Logger log = LoggerFactory.getLogger(DefaultIotDmEventListener.class);


    @EventListener
    @Async("taskExecutor")
    public void onDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        log.info(String.valueOf(deviceSessionEvent));

    }


    @EventListener
    @Async("taskExecutor")
    public void onDevicePropertyUpEvent(DevicePropertyUpEvent devicePropertyUpEvent){
        log.info(String.valueOf(devicePropertyUpEvent));
    }



    @EventListener
    @Async("taskExecutor")
    public void onDeviceMessageUpEvent(DeviceMessageUpEvent deviceMessageUpEvent){
        log.info(String.valueOf(deviceMessageUpEvent));
    }
}
