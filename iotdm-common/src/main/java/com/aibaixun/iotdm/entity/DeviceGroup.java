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
@TableName("t_device_group")
public class DeviceGroup extends BaseEntity {


    /**
     * 分组名称
     */
    private String groupLabel;

    /**
     * 描述
     */
    private String description;

    /**
     * 上级组id
     */
    private String superGroupId;


    public String getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuperGroupId() {
        return superGroupId;
    }

    public void setSuperGroupId(String superGroupId) {
        this.superGroupId = superGroupId;
    }

    @Override
    public String toString() {
        return "DeviceGroup{" +
                "groupLabel='" + groupLabel + '\'' +
                ", description='" + description + '\'' +
                ", superGroupId='" + superGroupId + '\'' +
                '}';
    }
}
