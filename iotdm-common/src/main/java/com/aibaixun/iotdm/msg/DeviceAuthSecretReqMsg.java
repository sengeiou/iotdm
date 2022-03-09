package com.aibaixun.iotdm.msg;

import java.io.Serializable;

/**
 * 设备密钥 认证消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DeviceAuthSecretReqMsg implements Serializable {



    private final String clientId;

    private final String username;

    private final String password;


    public DeviceAuthSecretReqMsg( String clientId, String username, String password) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }



    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "DeviceAuthSecretReqMsg{" +
                ", clientId='" + clientId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
