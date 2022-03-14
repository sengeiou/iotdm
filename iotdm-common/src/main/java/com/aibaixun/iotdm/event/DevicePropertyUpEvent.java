package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.DataFormat;

/**
 * 属性上报事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class DevicePropertyUpEvent extends BaseDataEvent{

    private DataFormat dataFormat;

    private String payload;

    public DevicePropertyUpEvent(String deviceId, String productId, DataFormat dataFormat, String payload) {
        super(deviceId, productId);
        this.dataFormat = dataFormat;
        this.payload = payload;
    }

    public DevicePropertyUpEvent() {
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

    @Override
    public String toString() {
        return "DevicePropertyUpEvent{" +
                "baseData=" + super.toString()+
                "dataFormat=" + dataFormat +
                ", payload='" + payload + '\'' +
                '}';
    }
}
