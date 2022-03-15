package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.transport.SessionId;

/**
 * 缓存服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public interface SessionCacheServer {


    /**
     * 添加session
     * @param sessionId sessionId
     * @param sessionInfo session 信息
     * @param ttl 缓存ttl
     */
    void addSessionCache(SessionId sessionId,TransportSessionInfo sessionInfo, long ttl);


    /**
     * 移除缓存
     * @param sessionId session 信息
     */
    void removeSessionCache(SessionId sessionId);


    /**
     * 活跃session
     * @param sessionId session 信息
     * @param ttl 更新ttl
     */
    void activitySessionCache(SessionId sessionId,long ttl);


    /**
     * 获取缓存
     * @param sessionId session id
     * @return session 信息
     */
    TransportSessionInfo getSessionFromCache(SessionId sessionId);
}
