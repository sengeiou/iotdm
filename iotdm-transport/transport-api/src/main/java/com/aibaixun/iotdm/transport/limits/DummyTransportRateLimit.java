package com.aibaixun.iotdm.transport.limits;

/**
 * 假的 限制器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class DummyTransportRateLimit implements TransportRateLimit{

    @Override
    public String getConfiguration() {
        return "";
    }

    @Override
    public boolean tryConsume() {
        return true;
    }

    @Override
    public boolean tryConsume(long number) {
        return true;
    }
}
