package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * mysql资源配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "MYSQL")
public class MySqlResourceConfig implements BaseResourceConfig{

    private String host;

    private String username;

    private String password;

    private Long connectPoolSize;

    private Boolean retryConnect;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.MYSQL;
    }

    @Override
    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
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

    public Long getConnectPoolSize() {
        return connectPoolSize;
    }

    public void setConnectPoolSize(Long connectPoolSize) {
        this.connectPoolSize = connectPoolSize;
    }

    public Boolean getRetryConnect() {
        return retryConnect;
    }

    public void setRetryConnect(Boolean retryConnect) {
        this.retryConnect = retryConnect;
    }
}
