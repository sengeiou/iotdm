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

    private Long metadataUpdateTime;

    private Long reqTimeout;

    private Long connectTimeOut;

    private String compressionType;

    private Long bufferSize;

    private Long batchSize;

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

    public Long getMetadataUpdateTime() {
        return metadataUpdateTime;
    }

    public void setMetadataUpdateTime(Long metadataUpdateTime) {
        this.metadataUpdateTime = metadataUpdateTime;
    }

    public Long getReqTimeout() {
        return reqTimeout;
    }

    public void setReqTimeout(Long reqTimeout) {
        this.reqTimeout = reqTimeout;
    }

    public Long getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }


    public Long getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Long getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Long batchSize) {
        this.batchSize = batchSize;
    }
}
