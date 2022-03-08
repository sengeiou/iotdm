package com.aibaixun.iotdm.transport.session;

import com.aibaixun.iotdm.msg.DeviceInfo;
import com.aibaixun.iotdm.msg.TransportSessionInfo;

import java.util.UUID;

/**
 * 设备 连接  session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class DeviceAwareSessionContext implements TransportSessionContext {


    protected final UUID sessionId;

    private volatile boolean connected;

    private TransportSessionInfo sessionInfo;

    protected DeviceAwareSessionContext(UUID sessionId) {
        this.sessionId = sessionId;
    }


    public boolean isConnected() {
        return connected;
    }

    public void setDisconnected() {
        this.connected = false;
    }


    public void setConnected() {
        this.connected = true;
    }

    @Override
    public UUID getSessionId() {
        return sessionId;
    }


    @Override
    public void onDeviceUpdate(TransportSessionInfo sessionInfo, DeviceInfo deviceInfo) {
        this.sessionInfo = sessionInfo;
    }

    public TransportSessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(TransportSessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
}
