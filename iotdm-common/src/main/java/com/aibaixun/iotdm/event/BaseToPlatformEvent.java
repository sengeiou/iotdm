package com.aibaixun.iotdm.event;


import java.io.Serializable;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public abstract class BaseToPlatformEvent implements Serializable {

    private String deviceId;

    private String productId;


    public BaseToPlatformEvent(String deviceId, String productId) {
        this.deviceId = deviceId;
        this.productId = productId;
    }

    public BaseToPlatformEvent() {
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
