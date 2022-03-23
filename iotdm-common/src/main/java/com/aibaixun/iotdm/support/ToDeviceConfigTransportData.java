package com.aibaixun.iotdm.support;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceConfigTransportData extends ToDeviceBaseData{

    private String host;

    private Integer port;


    private String clientId;


    private String username;

    private String password;

    public ToDeviceConfigTransportData(ToDeviceType toDeviceType) {
        super(toDeviceType);
    }


    public ToDeviceConfigTransportData(ToDeviceType toDeviceType, String host, Integer port, String clientId, String username, String password) {
        super(toDeviceType);
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
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
