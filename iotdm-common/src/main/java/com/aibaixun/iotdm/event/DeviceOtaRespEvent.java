package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.DataFormat;

/**
 * ota 升级反馈
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public class DeviceOtaRespEvent {

    private String deviceId;

    private String productId;

    private DataFormat dataFormat;

    private String payload;

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

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public DeviceOtaRespEvent() {
    }

    public DeviceOtaRespEvent(String deviceId, String productId, DataFormat dataFormat, String payload) {
        this.deviceId = deviceId;
        this.productId = productId;
        this.dataFormat = dataFormat;
        this.payload = payload;
    }
}
