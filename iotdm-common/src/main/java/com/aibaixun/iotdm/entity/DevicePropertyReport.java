package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


/**
 * <p>
 * 设备属性上报记录表 
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device_property_report")
public class DevicePropertyReport implements Serializable {

    private static final long serialVersionUID = 19876543210L;

    /**
     * id
     */
      private String id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 属性id
     */
    private String propertyId;

    /**
     * 属性值
     */
    private String propertyValue;

    /**
     * 时间
     */
    private Long ts;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "DevicePropertyReport{" +
        "id=" + id +
        ", deviceId=" + deviceId +
        ", propertyId=" + propertyId +
        ", propertyValue=" + propertyValue +
        ", ts=" + ts +
        "}";
    }
}
