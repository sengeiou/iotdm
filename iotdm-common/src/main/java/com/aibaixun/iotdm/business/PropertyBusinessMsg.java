package com.aibaixun.iotdm.business;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class PropertyBusinessMsg implements BusinessMsg{

    private MetaData metaData;


    private JsonNode propertyJsonNode;

    @Override
    public MetaData getMetaData() {
        return metaData;
    }

    @Override
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public PropertyBusinessMsg(MetaData metaData, JsonNode propertyJsonNode) {
        this.metaData = metaData;
        this.propertyJsonNode = propertyJsonNode;
    }

    public JsonNode getPropertyJsonNode() {
        return propertyJsonNode;
    }

    public void setPropertyJsonNode(JsonNode propertyJsonNode) {
        this.propertyJsonNode = propertyJsonNode;
    }
}
