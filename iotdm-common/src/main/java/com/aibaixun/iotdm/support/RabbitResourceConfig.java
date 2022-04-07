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

    private int port;

    private Long connectPoolSize;

    private String username;

    private String password;

    private Integer connectTimeout;

    private String virtualHost;

    private Integer keepLive;


    @Override
    public ResourceType getResourceType() {
        return ResourceType.RABBIT;
    }

    @Override
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

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getKeepLive() {
        return keepLive;
    }

    public void setKeepLive(Integer keepLive) {
        this.keepLive = keepLive;
    }
}
