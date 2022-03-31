package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.SessionEventType;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.service.DeviceInfoServer;
import com.aibaixun.iotdm.service.IotDmEventPublisher;
import com.aibaixun.iotdm.service.SessionCacheServer;
import com.aibaixun.iotdm.transport.*;
import com.aibaixun.iotdm.transport.limits.TransportLimitServer;
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

/**
 * 默认传输服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Service
public class DefaultTransportServer implements TransportServer {

    private DeviceInfoServer deviceInfoService;


    private SessionCacheServer sessionCacheService;


    private IotDmEventPublisher iotDmEventPublisher;


    private final Logger log  = LoggerFactory.getLogger(DefaultTransportServer.class);


    private TransportLimitServer transportLimitService;

    private ListenerContainer listenerContainer;


    @Value("${transport.default-keepalive}")
    private long defaultKeepalive;



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
    public void processDeviceDisConnect(SessionId sessionId, String hostName) {
        TransportSessionInfo sessionFromCache = sessionCacheService.getSessionFromCache(sessionId);
        var  lastConnect = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastConnectTime():null;
        var  lastActivity = Objects.nonNull(sessionFromCache)?sessionFromCache.getLastActivityTime():null;
        Futures.transform(deviceInfoService.setDeviceStatus2OffOnLine(sessionId.getDeviceId(),lastConnect,lastActivity,hostName),
                status -> null, MoreExecutors.directExecutor());
        Futures.submit(()->iotDmEventPublisher.publishDeviceSessionEvent(sessionId.getProductId(),sessionId.getDeviceId(), SessionEventType.DISCONNECT),MoreExecutors.directExecutor());
    }


    @Override
    public void processLogDevice(SessionId sessionId, String hostName) {
        log.info("DefaultTransportService.processLogDevice >> sessionId:{},deviceId:{},hostName:{}",sessionId,sessionId.getDeviceId(),hostName);
    }

    @Override
    public void registerSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener) {
        SessionId sessionId = transportSessionInfo.getSessionId();
        sessionCacheService.addSessionCache(sessionId,transportSessionInfo,defaultKeepalive);
        listenerContainer.computeIfAbsent(sessionId,k->new TransportSessionMetaData(transportSessionInfo.getDeviceId(),listener));
    }


    @Override
    public void deregisterSession(SessionId sessionId) {
        sessionCacheService.removeSessionCache(sessionId);
        listenerContainer.remove(sessionId);
    }


    @Override
    public void reportActivity(SessionId sessionId) {
        sessionCacheService.activitySessionCache(sessionId,defaultKeepalive);
    }


    @Override
    public void processPropertyUp(SessionId sessionId,  DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishPropertyUpEvent(sessionId.getProductId(), sessionId.getDeviceId(), dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processMessageUp(SessionId sessionId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishMessageUpEvent(sessionId.getProductId(), sessionId.getDeviceId(), dataFormat,payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processWarnUp(SessionId sessionId,  TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            sessionCacheService.removeSessionCache(sessionId);
            ListenableFuture<Void> listenableFuture = Futures.transform(deviceInfoService.setDeviceStatus2Warn(sessionId.getDeviceId()), status -> null, MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processPubAck(SessionId sessionId,  int msgId) {
        if (checkSessionAndLimit(sessionId)){
            Futures.transform(deviceInfoService.toDeviceMessageIsReceived(sessionId.getDeviceId(), msgId), status -> null, MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processConfigRespUp(SessionId sessionId, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishConfigRespUpEvent(sessionId.getProductId(), sessionId.getDeviceId() ), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    @Override
    public void processOtaRespUp(SessionId sessionId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishOtaRespUpEvent(sessionId.getProductId(), sessionId.getDeviceId(), dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    @Override
    public void processControlRespUp(SessionId sessionId, DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishControlRespEvent(sessionId.getProductId(), sessionId.getDeviceId(), dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }


    @Override
    public void processControlReqUp(SessionId sessionId,  DataFormat dataFormat, String payload, TransportServiceCallback<Void> callback) {
        if (checkSessionAndLimit(sessionId)){
            ListenableFuture<Void> listenableFuture = Futures.submit(() -> iotDmEventPublisher.publishControlReqEvent(sessionId.getProductId(), sessionId.getDeviceId(), dataFormat, payload), MoreExecutors.directExecutor());
            AsyncCallbackTemplate.withCallback(listenableFuture,callback::onSuccess,callback::onError,MoreExecutors.directExecutor());
        }
    }

    @Override
    public void processControlIsSend(Integer sendId, int msgId) {
         Futures.submit(() -> deviceInfoService.setControlMsgId(sendId,msgId), MoreExecutors.directExecutor());
    }

    /**
     * 校验session 与 限制
     * @param sessionId 会话信息
     * @return 是否成功
     */
    private boolean  checkSessionAndLimit(SessionId sessionId){

        if (log.isTraceEnabled()) {
            log.trace("DefaultTransportService.checkSessionAndLimit >>  Processing msg: {},{}", sessionId.getProductId(),sessionId.getDeviceId());
        }
        TransportSessionInfo sessionFromCache = sessionCacheService.getSessionFromCache(sessionId);
        if (Objects.isNull(sessionFromCache)){
            return false;
        }
        String tenantId = sessionFromCache.getTenantId();
        return transportLimitService.checkTenantLimit(tenantId);
    }

    @Autowired
    public void setDeviceInfoService(DeviceInfoServer deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Autowired
    public void setSessionCacheService(SessionCacheServer sessionCacheService) {
        this.sessionCacheService = sessionCacheService;
    }


    @Autowired
    public void setIotDmEventPublisher(IotDmEventPublisher iotDmEventPublisher) {
        this.iotDmEventPublisher = iotDmEventPublisher;
    }

    @Autowired
    public void setTransportLimitService(TransportLimitServer transportLimitService) {
        this.transportLimitService = transportLimitService;
    }

    @Autowired
    public void setListenerContainer(ListenerContainer listenerContainer) {
        this.listenerContainer = listenerContainer;
    }

}
