package com.aibaixun.iotdm.support;

import javax.validation.constraints.NotBlank;

/**
 * 子设备参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
public class SubDeviceParam {

    @NotBlank(message = "网关设备不允许为空")
    private String gatewayId;

    private String deviceLabel;

    @NotBlank(message = "所属产品不允许为空")
    private String productId;

    @NotBlank(message = "设备标识码不允许为空")
    private String deviceCode;

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

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



}
