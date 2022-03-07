package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 设备追踪
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device_trace")
public class DeviceTrace extends BaseEntity {


    /**
     * 设备id
     */
    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    /**
     * 是否debug
     */
    private Boolean traceDebug;

    /**
     * 追踪结束日期,最大3天
     */
    private Long traceTtl;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getTraceDebug() {
        return traceDebug;
    }

    public void setTraceDebug(Boolean traceDebug) {
        this.traceDebug = traceDebug;
    }

    public Long getTraceTtl() {
        return traceTtl;
    }

    public void setTraceTtl(Long traceTtl) {
        this.traceTtl = traceTtl;
    }


    @Override
    public String toString() {
        return "DeviceTrace{" +
                "deviceId='" + deviceId + '\'' +
                ", traceDebug=" + traceDebug +
                ", traceTtl=" + traceTtl +
                '}';
    }
}
