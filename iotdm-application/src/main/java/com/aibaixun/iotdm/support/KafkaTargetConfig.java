package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * kafka 目标配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "KAFKA")
public class KafkaTargetConfig implements BaseTargetConfig{

    private String topic;

    private String produceType;

    private String produceStrategy;

    private String messageKey;


    @Override
    public ResourceType getResourceType() {
        return ResourceType.KAFKA;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProduceType() {
        return produceType;
    }

    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public String getProduceStrategy() {
        return produceStrategy;
    }

    public void setProduceStrategy(String produceStrategy) {
        this.produceStrategy = produceStrategy;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
