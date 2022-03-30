package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * kafka 资源配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "KAFKA")
public class KafkaResourceConfig implements BaseResourceConfig{


    private String host;

    private String username;

    private String password;

    private Integer metadataUpdateTime;

    private Integer reqTimeout;

    private Integer connectTimeout;

    private String compressionType;

    private Integer bufferSize;

    private Integer batchSize;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.KAFKA;
    }


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

    public Integer getMetadataUpdateTime() {
        return metadataUpdateTime;
    }

    public void setMetadataUpdateTime(Integer metadataUpdateTime) {
        this.metadataUpdateTime = metadataUpdateTime;
    }

    public Integer getReqTimeout() {
        return reqTimeout;
    }

    public void setReqTimeout(Integer reqTimeout) {
        this.reqTimeout = reqTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }


    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }
}
