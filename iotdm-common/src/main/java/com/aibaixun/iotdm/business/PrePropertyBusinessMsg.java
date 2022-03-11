package com.aibaixun.iotdm.business;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class PrePropertyBusinessMsg extends AbstractBusinessMsg{

    private JsonNode propertyJsonNode;

    public PrePropertyBusinessMsg(MetaData metaData, JsonNode propertyJsonNode) {
        super(metaData);
        this.propertyJsonNode = propertyJsonNode;
    }

    public JsonNode getPropertyJsonNode() {
        return propertyJsonNode;
    }

    public void setPropertyJsonNode(JsonNode propertyJsonNode) {
        this.propertyJsonNode = propertyJsonNode;
    }
}
