package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.transport.SessionId;

import java.io.Serializable;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public abstract class BaseToDeviceEvent implements Serializable {


    private final SessionId sessionId;

    /**
     * 负载内容
     */
    private final String payload;



    public BaseToDeviceEvent(SessionId sessionId, String payload) {
        this.sessionId = sessionId;
        this.payload = payload;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public String getPayload() {
        return payload;
    }
}
