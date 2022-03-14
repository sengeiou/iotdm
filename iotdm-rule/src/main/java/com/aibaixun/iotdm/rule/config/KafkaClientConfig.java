package com.aibaixun.iotdm.rule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Configuration
public class KafkaClientConfig {

    @Value("${bx.rule.kafka.connect-timeout}")
    private Integer connectTimeout;

    @Value("${bx.rule.kafka.max-idle-connections}")
    private Integer maxIdleConnections;


    @Value("${bx.rule.kafka.keep-alive-duration}")
    private Long keepAliveDuration;


    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public Long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }
}
