package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.BusinessType;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 消息追踪
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_message_trace")
public class MessageTrace extends BaseEntity {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 业务类型
     */
    private BusinessType businessType;

    /**
     * 业务步骤
     */
    private String businessStep;

    /**
     * 业务详情
     */
    private String businessDetails;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getBusinessStep() {
        return businessStep;
    }

    public void setBusinessStep(String businessStep) {
        this.businessStep = businessStep;
    }

    public String getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(String businessDetails) {
        this.businessDetails = businessDetails;
    }

    @Override
    public String toString() {
        return "MessageTrace{" +
                "deviceId='" + deviceId + '\'' +
                ", businessType='" + businessType + '\'' +
                ", businessStep='" + businessStep + '\'' +
                ", businessDetails='" + businessDetails + '\'' +
                '}';
    }
}
