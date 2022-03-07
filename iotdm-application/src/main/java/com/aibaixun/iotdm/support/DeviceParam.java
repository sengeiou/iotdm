package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.DeviceAuthType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设备参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
public class DeviceParam {

    @NotBlank(message = "所属产品不能为空")
    private String productId;

    @NotBlank(message = "设备标识码码不允许为空")
    private String deviceCode;


    private String deviceLabel;

    @NotNull(message = "设备认证类型")
    private DeviceAuthType authType;


    private String deviceSecret;


    private String confirmSecret;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public DeviceAuthType getAuthType() {
        return authType;
    }

    public void setAuthType(DeviceAuthType authType) {
        this.authType = authType;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getConfirmSecret() {
        return confirmSecret;
    }

    public void setConfirmSecret(String confirmSecret) {
        this.confirmSecret = confirmSecret;
    }

    @Override
    public String toString() {
        return "DeviceParam{" +
                "productId='" + productId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceLabel='" + deviceLabel + '\'' +
                ", authType=" + authType +
                ", deviceSecret='" + deviceSecret + '\'' +
                ", confirmSecret='" + confirmSecret + '\'' +
                '}';
    }
}
