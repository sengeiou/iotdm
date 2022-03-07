package com.aibaixun.iotdm.params;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * RabbitMq 转发配置配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "RABBIT")
public class RabbitTargetConfig implements BaseTargetConfig{

    private String exchange;

    private String exchangeType;

    private String routingKey;

    private Boolean exchangeDurable;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.RABBIT;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Boolean getExchangeDurable() {
        return exchangeDurable;
    }

    public void setExchangeDurable(Boolean exchangeDurable) {
        this.exchangeDurable = exchangeDurable;
    }
}
