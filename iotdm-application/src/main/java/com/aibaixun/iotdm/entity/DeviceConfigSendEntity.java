package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.SendStatus;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
@TableName(value = "t_device_config_send",autoResultMap = true)
public class DeviceConfigSendEntity extends BaseEntity{

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 负载内容
     */
    private String payload;

    /**
     * 发送状态
     */
    private SendStatus sendStatus;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }


    @Override
    public String toString() {
        return "DeviceConfigSendEntity{" +
                "deviceId='" + deviceId + '\'' +
                ", payload='" + payload + '\'' +
                ", sendStatus=" + sendStatus +
                '}';
    }
}
