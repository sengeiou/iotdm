package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;

/**
 * Http 资源配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "HTTP")
public class HttpResourceConfig implements BaseResourceConfig{


    private Long connectTimeout;

    private Map<String,String> headers;

    private String host;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.HTTP;
    }


    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }
}
