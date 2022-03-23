package com.aibaixun.iotdm.event;

import java.io.Serializable;

/**
 * 设备更新配置反馈
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public class ConfigRespEvent implements Serializable {

    private String deviceId;

    private String productId;

    public ConfigRespEvent() {
    }

    public ConfigRespEvent(String deviceId, String productId) {
        this.deviceId = deviceId;
        this.productId = productId;
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
}
