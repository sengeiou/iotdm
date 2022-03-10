package com.aibaixun.iotdm.msg;

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
    public static TransportSessionInfo create (DeviceInfo deviceInfo, UUID sessionId){
        TransportSessionInfo sessionInfo = new TransportSessionInfo();
        sessionInfo.setSessionId(sessionId);
        sessionInfo.setDeviceId(deviceInfo.getDeviceId());
        sessionInfo.setDeviceCode(deviceInfo.getDeviceCode());
        sessionInfo.setProductId(deviceInfo.getProductId());
        sessionInfo.setProtocolType(deviceInfo.getProtocolType());
        sessionInfo.setNodeType(deviceInfo.getNodeType());
        sessionInfo.setDataFormat(deviceInfo.getDataFormat());
        sessionInfo.setTenantId(deviceInfo.getTenantId());
        sessionInfo.setLastConnectTime(Instant.now().toEpochMilli());
        return sessionInfo;
    }


    public static void activity (TransportSessionInfo sessionInfo){
        if (sessionInfo != null){
            sessionInfo.setLastActivityTime(Instant.now().toEpochMilli());
        }
    }
}
