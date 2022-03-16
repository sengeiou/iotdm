package com.aibaixun.iotdm.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设备追踪参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class TraceDeviceParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotNull(message = "追踪追踪时间不允许为空")
    @Max(value = 259200,message = "追踪时间最长不超过3天")
    private Long ttl;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
