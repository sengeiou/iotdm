package com.aibaixun.iotdm.msg;

import java.io.Serializable;

/**
 * 设备密钥 认证消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DeviceAuthSecretReqMsg implements Serializable {


    private String clientId;

    private String username;

    private String password;


    public DeviceAuthSecretReqMsg(String clientId, String username, String password) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "DeviceAuthSecretMsg{" +
                "clientId='" + clientId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
