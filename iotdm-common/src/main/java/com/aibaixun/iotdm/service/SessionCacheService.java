package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.msg.TransportSessionInfo;

import java.util.UUID;

/**
 * 缓存服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public interface SessionCacheService {


    /**
     * 添加session
     * @param sessionInfo session 信息
     * @param ttl 缓存ttl
     */
    void addSessionCache(TransportSessionInfo sessionInfo, long ttl);


    /**
     * 移除缓存
     * @param deviceId 设备id
     * @param sessionId session 信息
     */
    void removeSessionCache(UUID sessionId,String deviceId);


    /**
     * 活跃session
     * @param sessionId session 信息
     * @param deviceId 设备id
     * @param ttl 更新ttl
     */
    void activitySessionCache(UUID sessionId, String deviceId,long ttl);


    /**
     * 获取缓存
     * @param sessionId session id
     * @param deviceId 设备id
     * @return session 信息
     */
    TransportSessionInfo getSessionFromCache(UUID sessionId,String deviceId);
}
