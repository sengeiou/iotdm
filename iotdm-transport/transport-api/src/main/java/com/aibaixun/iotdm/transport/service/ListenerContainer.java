package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.transport.SessionId;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
@Component
public class ListenerContainer {

    /**
     * 存储 session 信息
     */
    private final ConcurrentMap<SessionId, TransportSessionMetaData> sessionListeners = new ConcurrentHashMap<>();

    public void addSessionMetaData(SessionId sessionId,TransportSessionMetaData metaData){
        sessionListeners.put(sessionId,metaData);
    }

    public void remove(SessionId sessionId){
        sessionListeners.remove(sessionId);
    }


    public TransportSessionMetaData getSessionMeta(SessionId sessionId){
        return sessionListeners.get(sessionId);
    }
}
