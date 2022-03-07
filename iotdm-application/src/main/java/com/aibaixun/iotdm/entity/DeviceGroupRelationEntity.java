package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 设备分组
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device_group_relation")
public class DeviceGroupRelationEntity extends BaseEntity {


    /**
     * 分组id
     */
    private String groupId;

    /**
     * 设备id
     */
    private String deviceId;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "DeviceGroupRelation{" +
                "groupId='" + groupId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
