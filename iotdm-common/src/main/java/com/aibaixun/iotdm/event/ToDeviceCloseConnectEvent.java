package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.transport.SessionId;

import java.io.Serializable;

/**
 * 关闭设备当前连接
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceCloseConnectEvent implements Serializable {

    private SessionId sessionId;

    public ToDeviceCloseConnectEvent(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }
}
