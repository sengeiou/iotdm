package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.service.DeviceInfoService;
import com.aibaixun.iotdm.service.SessionCacheService;
import com.aibaixun.iotdm.transport.MqttTransportException;
import com.aibaixun.iotdm.transport.TransportService;
import com.aibaixun.iotdm.transport.TransportServiceCallback;
import com.aibaixun.iotdm.transport.TransportSessionListener;
import com.aibaixun.iotdm.util.AsyncCallbackTemplate;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
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


    private SessionCacheService sessionCacheService;


    private final Logger log  = LoggerFactory.getLogger(DefaultTransportService.class);


    /**
     * 存储 session 信息
     */
    private final ConcurrentMap<UUID, TransportSessionMetaData> sessionListeners = new ConcurrentHashMap<>();

    @Override
    public void processDeviceAuthBySecret(ProtocolType protocolType, DeviceAuthSecretReqMsg deviceAuthSecretReqMsg, TransportServiceCallback<DeviceAuthRespMsg> callback) {
        ListenableFuture<DeviceAuthRespMsg> listenableFuture = Futures.transform(deviceInfoService.mqttDeviceAuthBySecret(deviceAuthSecretReqMsg), deviceInfo -> {
            DeviceAuthRespMsg deviceAuthRespMsg = new DeviceAuthRespMsg();
            if (Objects.nonNull(deviceInfo)) {
                if (!deviceInfo.getProtocolType().equals(protocolType)){
                    throw new MqttTransportException((byte)1);
                }
                if (StringUtils.isEmpty(deviceInfo.getProductId())){
                    throw new MqttTransportException((byte)5);
                }
                deviceAuthRespMsg.setDeviceInfo(deviceInfo);
            }
            return deviceAuthRespMsg;
        }, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
    }

    @Override
    public void processDeviceConnectSuccess(TransportSessionInfo sessionInfo, TransportServiceCallback<Boolean> callback) {
        ListenableFuture<Boolean> setDeviceStatus = Futures.transform(deviceInfoService.setDeviceStatus2OnLine(sessionInfo.getDeviceId()),
                status -> status, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(setDeviceStatus,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
    }

    @Override
    public void processDeviceDisConnect(UUID sessionId, String deviceId,String hostName) {
        TransportSessionInfo sessionFromCache = sessionCacheService.getSessionFromCache(sessionId, deviceId);
        var  lastConnect = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastConnectTime():null;
        var  lastCActivity = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastActivityTime():null;
        Futures.transform(deviceInfoService.setDeviceStatus2OffOnLine(deviceId,lastConnect,lastCActivity,hostName),
                status -> null, MoreExecutors.directExecutor());
    }


    @Override
    public void processLogDevice(UUID sessionId, String deviceId,String hostName) {
        log.info("DefaultTransportService.processLogDevice >> sessionId:{},deviceId:{},hostName:{}",sessionId,deviceId,hostName);
    }

    @Override
    public void registerSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener) {
        sessionCacheService.addSessionCache(transportSessionInfo,90);
        sessionListeners.computeIfAbsent(transportSessionInfo.getSessionId(),k->new TransportSessionMetaData(transportSessionInfo.getDeviceId(),listener));
    }


    @Override
    public void deregisterSession(UUID sessionId,String deviceId) {
        sessionCacheService.removeSessionCache(sessionId,deviceId);
        sessionListeners.remove(sessionId);
    }


    @Override
    public void reportActivity(UUID sessionId,String deviceId) {
        sessionCacheService.activitySessionCache(sessionId,deviceId,90);
    }

    @Autowired
    public void setDeviceInfoService(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Autowired
    public void setSessionCacheService(SessionCacheService sessionCacheService) {
        this.sessionCacheService = sessionCacheService;
    }
}
