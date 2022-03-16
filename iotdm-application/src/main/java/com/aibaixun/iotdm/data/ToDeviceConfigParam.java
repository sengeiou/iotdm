package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送到设备的配置参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceConfigParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotBlank(message = "连接地址不允许为空")
    private String host;


    @NotNull(message = "连接端口不允许为空")
    private Integer port;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
