package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * RabbitMq 资源配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "RABBIT")
public class RabbitResourceConfig implements BaseResourceConfig{


    private String host;

    private Long connectPoolSize;

    private String username;

    private String password;

    private Long connectTimeout;

    private String virtualHost;

    private Long keepLive;

    private Long retryConnectInterval;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.RABBIT;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getConnectPoolSize() {
        return connectPoolSize;
    }

    public void setConnectPoolSize(Long connectPoolSize) {
        this.connectPoolSize = connectPoolSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public Long getKeepLive() {
        return keepLive;
    }

    public void setKeepLive(Long keepLive) {
        this.keepLive = keepLive;
    }

    public Long getRetryConnectInterval() {
        return retryConnectInterval;
    }

    public void setRetryConnectInterval(Long retryConnectInterval) {
        this.retryConnectInterval = retryConnectInterval;
    }
}
