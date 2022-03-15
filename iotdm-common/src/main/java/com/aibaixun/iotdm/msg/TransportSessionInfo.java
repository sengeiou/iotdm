package com.aibaixun.iotdm.msg;


import com.aibaixun.iotdm.transport.SessionId;

/**
 * 传输session 信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class TransportSessionInfo {

    private SessionId sessionId;

    private String tenantId;

    private String deviceId;

    private String productId;

    private long lastConnectTime;

    private long  lastActivityTime;

    protected TransportSessionInfo() {}


    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(long lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }




}
