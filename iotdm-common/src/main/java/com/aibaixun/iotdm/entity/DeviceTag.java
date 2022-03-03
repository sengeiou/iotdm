package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 设备标签
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device_tag")
public class DeviceTag extends BaseEntity {

    /**
     * 标签id
     */
    private String tagKey;

    /**
     * 标签内容
     */
    private String tagValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 设备id
     */
    private String deviceId;


    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "DeviceTag{" +
                "tagKey='" + tagKey + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", description='" + description + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
