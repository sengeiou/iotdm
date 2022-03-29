package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.entity.DeviceEntity;

/**
 * 设备信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class DeviceEntityInfo extends DeviceEntity {

    private String productLabel;

    public DeviceEntityInfo() {
    }

    public DeviceEntityInfo(DeviceEntity deviceEntity, String productLabel) {
        this.productLabel = productLabel;
        setDeviceStatus(deviceEntity.getDeviceStatus());
        setDeviceLabel(deviceEntity.getDeviceLabel());
        setDeviceCode(deviceEntity.getDeviceCode());
        setId(deviceEntity.getId());
        setProductId(deviceEntity.getProductId());
        setNodeType(deviceEntity.getNodeType());
        setAuthType(deviceEntity.getAuthType());
        setDeviceSecret(deviceEntity.getDeviceSecret());
        setCreateTime(deviceEntity.getCreateTime());
        setLastConnectTs(deviceEntity.getLastConnectTs());
        setLastActivityTs(deviceEntity.getLastActivityTs());
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }
}
