package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;

/**
 * 设备名称更改 参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
public class UpdateDeviceLabelParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotBlank(message = "设备名称不允许为空")
    private String deviceLabel;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    @Override
    public String toString() {
        return "UpdateDeviceLabelParam{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceLabel='" + deviceLabel + '\'' +
                '}';
    }
}
