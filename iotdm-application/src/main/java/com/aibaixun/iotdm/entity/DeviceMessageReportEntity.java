package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


/**
 * <p>
 * 设备消息上报
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device_message_report")
public class DeviceMessageReportEntity implements Serializable {

    private static final long serialVersionUID = 10987654321L;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 负载内容
     */
    private String payload;

    /**
     * 创建时间
     */
    private Long ts;




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

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "DeviceMessageReport{" +
        ", deviceId=" + deviceId +
        ", payload=" + payload +
        ", ts=" + ts +
        "}";
    }
}
