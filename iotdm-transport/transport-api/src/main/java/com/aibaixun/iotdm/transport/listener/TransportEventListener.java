package com.aibaixun.iotdm.transport.listener;

import com.aibaixun.iotdm.event.ToDeviceDisConnectEvent;
import com.aibaixun.iotdm.event.ToDeviceConfigEvent;
import com.aibaixun.iotdm.event.ToDeviceControlEvent;
import com.aibaixun.iotdm.event.ToDeviceOtaEvent;
import com.aibaixun.iotdm.transport.SessionId;
import com.aibaixun.iotdm.transport.service.ListenerContainer;
import com.aibaixun.iotdm.transport.service.TransportSessionMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * transport event listener
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
@Component
public class TransportEventListener {

    private ListenerContainer listenerContainer;


    @EventListener
    @Async("taskExecutor")
    public void process2DeviceConfigRequest(ToDeviceConfigEvent deviceConfigEvent){
        SessionId sessionId = deviceConfigEvent.getSessionId();
        if (Objects.isNull(sessionId)){
            return;
        }
        TransportSessionMetaData sessionMeta = listenerContainer.getSessionMeta(sessionId);
        if (Objects.isNull(sessionMeta)){
            return;
        }
        String payload = deviceConfigEvent.getPayload();
        sessionMeta.getListener().on2DeviceConfigReq(payload);
    }


    @EventListener
    @Async("taskExecutor")
    public void process2DeviceOtaRequest(ToDeviceOtaEvent deviceOtaEvent){
        SessionId sessionId = deviceOtaEvent.getSessionId();
        if (Objects.isNull(sessionId)){
            return;
        }
        TransportSessionMetaData sessionMeta = listenerContainer.getSessionMeta(sessionId);
        if (Objects.isNull(sessionMeta)){
            return;
        }
        String payload = deviceOtaEvent.getPayload();
        sessionMeta.getListener().on2DeviceOtaReq(payload);
    }


    @EventListener
    @Async("taskExecutor")
    public void process2DeviceControlRequest(ToDeviceControlEvent deviceControlEvent){
        SessionId sessionId = deviceControlEvent.getSessionId();
        if (Objects.isNull(sessionId)){
            return;
        }
        TransportSessionMetaData sessionMeta = listenerContainer.getSessionMeta(sessionId);
        if (Objects.isNull(sessionMeta)){
            return;
        }
        String payload = deviceControlEvent.getPayload();
        sessionMeta.getListener().on2DeviceControlReq(deviceControlEvent.getSendId(),payload);
    }

    @EventListener
    @Async("taskExecutor")
    public void process2DeviceCloseConnect(ToDeviceDisConnectEvent toDeviceCloseConnectEvent){
        SessionId sessionId = toDeviceCloseConnectEvent.getSessionId();
        TransportSessionMetaData sessionMeta = listenerContainer.getSessionMeta(sessionId);
        if (Objects.isNull(sessionMeta)){
            return;
        }
        sessionMeta.getListener().onCloseConnect();
    }




    @Autowired
    public void setListenerContainer(ListenerContainer listenerContainer) {
        this.listenerContainer = listenerContainer;
    }
}
