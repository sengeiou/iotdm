package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.enums.DeviceStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public class UpdateDeviceStatusParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotNull(message = "设备状态不允许为空")
    private DeviceStatus deviceStatus;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}
