package com.aibaixun.iotdm.transport.session;


/**
 * 设备 连接  session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class DeviceAwareSessionContext implements TransportSessionContext {

    private volatile boolean connected;

    public boolean isConnected() {
        return connected;
    }

    public void setDisconnected() {
        this.connected = false;
    }


    public void setConnected() {
        this.connected = true;
    }

}
