package com.aibaixun.iotdm.msg;

import com.aibaixun.iotdm.transport.SessionId;

import java.time.Instant;
import java.util.UUID;

/**
 * 传输session 信息创建者
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class TransportSessionInfoHolder {

    private TransportSessionInfoHolder () {}

    /**
     * 创建session 信息
     * @param deviceInfo 设备信息
     * @param sessionId  sessionId
     * @return session
     */
    public static TransportSessionInfo create (DeviceInfo deviceInfo){
        TransportSessionInfo sessionInfo = new TransportSessionInfo();
        sessionInfo.setDeviceId(deviceInfo.getDeviceId());
        sessionInfo.setProductId(deviceInfo.getProductId());
        sessionInfo.setTenantId(deviceInfo.getTenantId());
        sessionInfo.setLastConnectTime(Instant.now().toEpochMilli());
        SessionId sessionId = new SessionId( deviceInfo.getDeviceId(),deviceInfo.getProductId());
        sessionInfo.setSessionId(sessionId);
        return sessionInfo;
    }


    public static void activity (TransportSessionInfo sessionInfo){
        if (sessionInfo != null){
            sessionInfo.setLastActivityTime(Instant.now().toEpochMilli());
        }
    }
}
