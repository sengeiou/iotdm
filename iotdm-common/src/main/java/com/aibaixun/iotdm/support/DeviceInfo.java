package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.entity.Device;

/**
 * 设备信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class DeviceInfo extends Device {

    private String productLabel;

    public DeviceInfo() {
    }

    public DeviceInfo(Device device, String productLabel) {
        this.productLabel = productLabel;
        setDeviceStatus(device.getDeviceStatus());
        setDeviceLabel(device.getDeviceLabel());
        setDeviceCode(getDeviceCode());
        setId(device.getId());
        setProductId(device.getProductId());
        setNodeType(device.getNodeType());
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }
}
