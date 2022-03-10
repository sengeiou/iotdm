package com.aibaixun.iotdm.transport.limits;

/**
 * 连接限制器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface TransportRateLimit {

    /**
     * 获取配置
     * @return Strng
     */
    String getConfiguration();

    /**
     * tryConsume
     * @return boolean
     */
    boolean tryConsume();

    /**
     * tryConsume
     * @param number  消费 number
     * @return boolean
     */
    boolean tryConsume(long number);
}
