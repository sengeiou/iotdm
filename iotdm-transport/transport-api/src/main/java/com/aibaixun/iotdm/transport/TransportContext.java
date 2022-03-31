package com.aibaixun.iotdm.transport;

import com.aibaixun.iotdm.transport.limits.TransportLimitServer;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 传输上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class TransportContext {

    protected TransportServer transportService;

    protected TransportLimitServer transportRateLimitService;



    @Autowired
    public void setTransportService(TransportServer transportService) {
        this.transportService = transportService;
    }

    @Autowired
    public void setTransportRateLimitService(TransportLimitServer transportRateLimitService) {
        this.transportRateLimitService = transportRateLimitService;
    }


    public TransportServer getTransportService() {
        return transportService;
    }

    public TransportLimitServer getTransportRateLimitService() {
        return transportRateLimitService;
    }



}
