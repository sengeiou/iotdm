package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.msg.SessionEventType;

/**
 * 设备 session 连接事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class DeviceSessionEvent extends BaseDataEvent{

    public DeviceSessionEvent(String deviceId, String productId, SessionEventType sessionEventType) {
        super(deviceId, productId);
        this.sessionEventType = sessionEventType;
    }

    public DeviceSessionEvent() {
    }

    private SessionEventType sessionEventType;


    public SessionEventType getSessionEventType() {
        return sessionEventType;
    }

    public void setSessionEventType(SessionEventType sessionEventType) {
        this.sessionEventType = sessionEventType;
    }


    @Override
    public String toString() {
        return "DeviceSessionEvent{" +
                "baseData=" + super.toString() +
                "sessionEventType=" + sessionEventType +
                '}';
    }
}
