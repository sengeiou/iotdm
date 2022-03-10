package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.SessionEventType;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.service.DeviceInfoService;
import com.aibaixun.iotdm.service.IotDmEventPublisher;
import com.aibaixun.iotdm.service.SessionCacheService;
import com.aibaixun.iotdm.transport.MqttTransportException;
import com.aibaixun.iotdm.transport.TransportService;
import com.aibaixun.iotdm.transport.TransportServiceCallback;
import com.aibaixun.iotdm.transport.TransportSessionListener;
import com.aibaixun.iotdm.transport.limits.TransportLimitService;
import com.aibaixun.iotdm.util.AsyncCallbackTemplate;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    private IotDmEventPublisher iotDmEventPublisher;


    private final Logger log  = LoggerFactory.getLogger(DefaultTransportService.class);


    private TransportLimitService transportLimitService;


    @Value("${transport.default-keepalive}")
    private long defaultKeepalive;

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
        Futures.submit(()->iotDmEventPublisher.publishDeviceSessionEvent(sessionInfo.getProductId(),sessionInfo.getDeviceId(), SessionEventType.CONNECT),MoreExecutors.directExecutor());
    }

    @Override
    public void processDeviceDisConnect(UUID sessionId, String productId,String deviceId,String hostName) {
        TransportSessionInfo sessionFromCache = sessionCacheService.getSessionFromCache(sessionId, deviceId);
        var  lastConnect = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastConnectTime():null;
        var  lastActivity = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastActivityTime():null;
        Futures.transform(deviceInfoService.setDeviceStatus2OffOnLine(deviceId,lastConnect,lastActivity,hostName),
                status -> null, MoreExecutors.directExecutor());

        Futures.submit(()->iotDmEventPublisher.publishDeviceSessionEvent(productId,deviceId, SessionEventType.DISCONNECT),MoreExecutors.directExecutor());
    }


    @Override
    public void processLogDevice(UUID sessionId, String deviceId,String hostName) {
        log.info("DefaultTransportService.processLogDevice >> sessionId:{},deviceId:{},hostName:{}",sessionId,deviceId,hostName);
    }

    @Override
    public void registerSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener) {
        sessionCacheService.addSessionCache(transportSessionInfo,defaultKeepalive);
        sessionListeners.computeIfAbsent(transportSessionInfo.getSessionId(),k->new TransportSessionMetaData(transportSessionInfo.getDeviceId(),listener));
    }


    @Override
    public void deregisterSession(UUID sessionId,String deviceId) {
        sessionCacheService.removeSessionCache(sessionId,deviceId);
        sessionListeners.remove(sessionId);
    }


    @Override
    public void reportActivity(UUID sessionId,String deviceId) {
        sessionCacheService.activitySessionCache(sessionId,deviceId,defaultKeepalive);
    }


    @Override
    public void processPropertyUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishPropertyUpEvent(productId, deviceId, dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processMessageUp(UUID sessionId, String deviceId, String productId, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishMessageUpEvent(productId, deviceId, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processWarnUp(UUID sessionId, String deviceId, String productId, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            sessionCacheService.removeSessionCache(sessionId,deviceId);
            ListenableFuture<Void> listenableFuture = Futures.transform(deviceInfoService.setDeviceStatus2Warn(deviceId), status -> null, MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processPubAck(UUID sessionId, String deviceId, int msgId) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            Futures.transform(deviceInfoService.toDeviceMessageIsReceived(deviceId, msgId), status -> null, MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processConfigRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishConfigRespUpEvent(productId, deviceId, dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    @Override
    public void processOtaRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishConfigOtaRespUpEvent(productId, deviceId, dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    @Override
    public void processControlRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishControlRespEvent(productId, deviceId, dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processControlReqUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId,deviceId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishControlReqEvent(productId, deviceId, dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    /**
     * 校验session 与 限制
     * @param sessionId 会话信息
     * @param deviceId 设备id
     * @return 是否成功
     */
    private boolean  checkSessionAndLimit(UUID sessionId, String deviceId){

        if (log.isTraceEnabled()) {
            log.trace("DefaultTransportService.checkSessionAndLimit >>  Processing msg: {},{}", sessionId,deviceId);
        }
        TransportSessionInfo sessionFromCache = sessionCacheService.getSessionFromCache(sessionId, deviceId);
        if (Objects.isNull(sessionFromCache)){
            return false;
        }
        String tenantId = sessionFromCache.getTenantId();
        return transportLimitService.checkTenantLimit(tenantId);
    }

    @Autowired
    public void setDeviceInfoService(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Autowired
    public void setSessionCacheService(SessionCacheService sessionCacheService) {
        this.sessionCacheService = sessionCacheService;
    }


    @Autowired
    public void setIotDmEventPublisher(IotDmEventPublisher iotDmEventPublisher) {
        this.iotDmEventPublisher = iotDmEventPublisher;
    }

    @Autowired
    public void setTransportLimitService(TransportLimitService transportLimitService) {
        this.transportLimitService = transportLimitService;
    }
}
