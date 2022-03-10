package com.aibaixun.iotdm.transport.limits;

import com.aibaixun.iotdm.util.IotDmRateLimits;

/**
 * 限制器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class SimpleTransportRateLimit implements TransportRateLimit{

    private final IotDmRateLimits rateLimits;

    private final String configuration;


    public SimpleTransportRateLimit( String configuration) {
        this.configuration = configuration;
        this.rateLimits = new IotDmRateLimits(configuration);
    }

    @Override
    public String getConfiguration() {
        return configuration;
    }

    @Override
    public boolean tryConsume() {
        return rateLimits.tryConsume();
    }

    @Override
    public boolean tryConsume(long number) {
        return rateLimits.tryConsume(number);
    }


}
