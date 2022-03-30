package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.DataFormat;

import java.io.Serializable;

/**
 * 设备 命令反馈
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public class DeviceControlRespEvent extends BaseToPlatformEvent {


    private DataFormat dataFormat;

    private String payload;

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

    public DeviceControlRespEvent(String deviceId, String productId, DataFormat dataFormat, String payload) {
        super(deviceId, productId);
        this.dataFormat = dataFormat;
        this.payload = payload;
    }

    public DeviceControlRespEvent() {
    }
}
