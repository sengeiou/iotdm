package com.aibaixun.iotdm.server;

import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.msg.TransportSessionInfoHolder;
import com.aibaixun.iotdm.service.SessionCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * session cache 服务类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Service
public class SessionCacheServiceImpl implements SessionCacheService {


    private RedisRepository redisRepository;

    @Override
    public void addSessionCache(TransportSessionInfo sessionInfo, long ttl) {
        redisRepository.setExpire(generatorSessionKey(sessionInfo.getSessionId(), sessionInfo.getDeviceId()),sessionInfo,ttl);
    }

    @Override
    public void removeSessionCache(UUID sessionId,String deviceId) {
        Object o = redisRepository.get(generatorSessionKey(sessionId,deviceId));
        redisRepository.del(generatorSessionKey(sessionId,deviceId));
    }


    @Override
    public void activitySessionCache(UUID sessionId,String deviceId, long ttl) {
        TransportSessionInfo sessionInfo = ((TransportSessionInfo) redisRepository.get(generatorSessionKey(sessionId,deviceId)));
        TransportSessionInfoHolder.activity(sessionInfo);
        addSessionCache(sessionInfo, ttl);
    }


    @Override
    public TransportSessionInfo getSessionFromCache(UUID sessionId,String deviceId) {
        Object o = redisRepository.get(generatorSessionKey(sessionId,deviceId));
        return ((TransportSessionInfo) o);
    }

    private String generatorSessionKey (UUID sessionId,String deviceId) {
        return String.format(DataConstants.IOT_SESSION_CACHE_KEY_PREFIX +"%s:%s",sessionId,deviceId);
    }

    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

}
