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


    private String clientId;


    private String username;


    private String password;



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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
