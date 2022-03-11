package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.DeviceSessionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class SessionProcessor {

    private final Logger log = LoggerFactory.getLogger(SessionProcessor.class);


    public void doProcessDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        log.info(String.valueOf(deviceSessionEvent));
    }
}
