package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;

/**
 * 发送到设备的ota配置参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceOtaParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotBlank(message = "OTA升级包不允许为空")
    private String otaId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }
}
