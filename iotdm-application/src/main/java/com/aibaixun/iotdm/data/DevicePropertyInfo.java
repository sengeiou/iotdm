package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.enums.DataType;
import com.aibaixun.iotdm.enums.ParamScope;

/**
 * 设备属性
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
public class DevicePropertyInfo {

    private String propertyId;

    private String propertyLabel;

    private String propertyValue;

    private String deviceId;

    private String modelLabel;

    private DataType dataType;

    private Long ts;

    private ParamScope scope;


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

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public ParamScope getScope() {
        return scope;
    }

    public void setScope(ParamScope scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "DevicePropertyInfo{" +
                "propertyId='" + propertyId + '\'' +
                ", propertyLabel='" + propertyLabel + '\'' +
                ", propertyValue='" + propertyValue + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", modelLabel='" + modelLabel + '\'' +
                ", dataType=" + dataType +
                ", ts=" + ts +
                '}';
    }
}
