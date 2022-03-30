package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.DataFormat;

/**
 * 消息上报
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class DeviceMessageUpEvent extends BaseToPlatformEvent {

    private String payload;

    private DataFormat dataFormat;


    public DeviceMessageUpEvent(String deviceId, String productId,  DataFormat dataFormat,String payload) {
        super(deviceId, productId);
        this.payload = payload;
        this.dataFormat = dataFormat;
    }

    public DeviceMessageUpEvent() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public String toString() {
        return "DeviceMessageUpEvent{" +
                "baseData=" + super.toString()+
                "payload='" + payload + '\'' +
                '}';
    }
}
