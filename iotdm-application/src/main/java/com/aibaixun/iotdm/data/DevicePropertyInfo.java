package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.enums.DataType;

/**
 * 设备属性
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
public class DevicePropertyInfo {

    private String propertyId;

    private String propertyLabel;

    private String deviceId;

    private String modelLabel;

    private DataType dataType;

    private Long ts;


    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyLabel() {
        return propertyLabel;
    }

    public void setPropertyLabel(String propertyLabel) {
        this.propertyLabel = propertyLabel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String modelLabel) {
        this.modelLabel = modelLabel;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }


    @Override
    public String toString() {
        return "DevicePropertyInfo{" +
                "propertyId='" + propertyId + '\'' +
                ", propertyLabel='" + propertyLabel + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", modelLabel='" + modelLabel + '\'' +
                ", dataType=" + dataType +
                ", ts=" + ts +
                '}';
    }
}
