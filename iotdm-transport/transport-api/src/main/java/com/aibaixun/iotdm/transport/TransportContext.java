package com.aibaixun.iotdm.transport;

import com.aibaixun.iotdm.scheduler.SchedulerComponent;
import com.aibaixun.iotdm.transport.limits.TransportLimitService;
import com.aibaixun.iotdm.util.IotDmExecutors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;

/**
 * 传输上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class TransportContext {

    protected TransportService transportService;

    protected TransportLimitService transportRateLimitService;

    private SchedulerComponent scheduler;


    private ExecutorService executor;


    protected final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init (){
            executor = IotDmExecutors.newWorkStealingPool(50, getClass());
    }

    @PreDestroy
    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }


    @Autowired
    public void setScheduler(SchedulerComponent scheduler) {
        this.scheduler = scheduler;
    }

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

    public ObjectMapper getMapper() {
        return mapper;
    }

    public SchedulerComponent getScheduler() {
        return scheduler;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
