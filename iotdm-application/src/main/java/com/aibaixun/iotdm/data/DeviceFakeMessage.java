package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;

/**
 * 设备模拟消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/21
 */
public class DeviceFakeMessage {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotBlank(message = "模拟消息不能为空")
    private String message;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
