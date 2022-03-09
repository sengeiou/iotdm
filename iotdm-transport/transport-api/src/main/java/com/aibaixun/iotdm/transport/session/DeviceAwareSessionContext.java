package com.aibaixun.iotdm.transport.session;



import java.util.UUID;

/**
 * 设备 连接  session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class DeviceAwareSessionContext implements TransportSessionContext {


    protected final UUID sessionId;

    private volatile boolean connected;


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


}
