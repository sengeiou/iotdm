package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.support.ToDeviceBaseData;
import com.aibaixun.iotdm.support.ToDeviceType;
import com.aibaixun.iotdm.transport.SessionId;

/**
 * 关闭设备当前连接
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceDisConnectEvent extends ToDeviceBaseData {

    private SessionId sessionId;

    public ToDeviceDisConnectEvent(SessionId sessionId) {
        super(ToDeviceType.SESSION);
        this.sessionId = sessionId;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }
}
