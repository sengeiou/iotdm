package com.aibaixun.iotdm.server;

import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.msg.TransportSessionInfoHolder;
import com.aibaixun.iotdm.service.SessionCacheServer;
import com.aibaixun.iotdm.transport.SessionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * session cache 服务类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Service
public class DefaultSessionCacheServer implements SessionCacheServer {


    private RedisRepository redisRepository;

    @Override
    public void addSessionCache(SessionId sessionId,TransportSessionInfo sessionInfo, long ttl) {
        redisRepository.setExpire(generatorSessionKey(sessionId),sessionInfo,ttl);
    }

    @Override
    public void removeSessionCache(SessionId sessionId) {
        Object o = redisRepository.get(generatorSessionKey(sessionId));
        redisRepository.del(generatorSessionKey(sessionId));
    }


    @Override
    public void activitySessionCache(SessionId sessionId, long ttl) {
        TransportSessionInfo sessionInfo = ((TransportSessionInfo) redisRepository.get(generatorSessionKey(sessionId)));
        TransportSessionInfoHolder.activity(sessionInfo);
        addSessionCache(sessionId,sessionInfo, ttl);
    }


    @Override
    public TransportSessionInfo getSessionFromCache(SessionId sessionId) {
        Object o = redisRepository.get(generatorSessionKey(sessionId));
        return ((TransportSessionInfo) o);
    }

    private String generatorSessionKey (SessionId sessionId) {
        return String.format(DataConstants.IOT_SESSION_CACHE_KEY_PREFIX +"%s:%s",sessionId.getProductId(),sessionId.getDeviceId());
    }

    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

}
