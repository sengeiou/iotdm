package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.service.DeviceInfoService;
import com.aibaixun.iotdm.transport.TransportService;
import com.aibaixun.iotdm.transport.TransportServiceCallback;
import com.aibaixun.iotdm.transport.TransportSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 默认传输服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Service
public class DefaultTransportService implements TransportService {



    private DeviceInfoService deviceInfoService;


    /**
     * 存储 session 信息
     */
    private final ConcurrentMap<UUID, TransportSessionMetaData> sessions = new ConcurrentHashMap<>();

    @Override
    public void processDeviceAuthBySecret(ProtocolType protocolType, DeviceAuthSecretReqMsg deviceAuthSecretReqMsg, TransportServiceCallback<DeviceAuthRespMsg> callback) {

    }

    @Override
    public void processDeviceConnectSuccess(TransportSessionInfo sessionInfo, TransportServiceCallback<Void> callback) {

    }

    @Override
    public void processDeviceDisConnect(TransportSessionInfo sessionInfo, TransportServiceCallback<Void> callback) {

    }

    @Override
    public void registerSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener) {
         sessions.computeIfAbsent(transportSessionInfo.getSessionId(), k-> new TransportSessionMetaData(transportSessionInfo, listener));
    }


    @Override
    public void deregisterSession(TransportSessionInfo sessionInfo) {
        sessions.remove(sessionInfo.getSessionId());
    }


    @Override
    public void reportActivity(TransportSessionInfo sessionInfo) {

    }

    @Autowired
    public void setDeviceInfoService(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }
}
