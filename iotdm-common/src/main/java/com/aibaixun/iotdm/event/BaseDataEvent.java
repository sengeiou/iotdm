package com.aibaixun.iotdm.event;

import com.aibaixun.common.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public abstract class BaseDataEvent implements DataEvent{

    private String deviceId;

    private String productId;



    public BaseDataEvent(String deviceId, String productId) {
        this.deviceId = deviceId;
        this.productId = productId;
    }

   public JsonNode getMetaData () {
       ObjectNode objectNode = JsonUtil.createObjNode();
       objectNode.put("deviceId",deviceId);
       objectNode.put("productId",productId);
       return objectNode;
   }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    @Override
    public String toString() {
        return "BaseDataEvent{" +
                "deviceId='" + deviceId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
