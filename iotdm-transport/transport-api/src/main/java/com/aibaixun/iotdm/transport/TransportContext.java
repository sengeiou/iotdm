package com.aibaixun.iotdm.transport;

import com.aibaixun.iotdm.transport.limits.TransportLimitService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 传输上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class TransportContext {

    protected TransportService transportService;

    protected TransportLimitService transportRateLimitService;



    @Autowired
    public void setTransportService(TransportService transportService) {
        this.transportService = transportService;
    }

    @Autowired
    public void setTransportRateLimitService(TransportLimitService transportRateLimitService) {
        this.transportRateLimitService = transportRateLimitService;
    }


    public TransportService getTransportService() {
        return transportService;
    }

    public TransportLimitService getTransportRateLimitService() {
        return transportRateLimitService;
    }



}
