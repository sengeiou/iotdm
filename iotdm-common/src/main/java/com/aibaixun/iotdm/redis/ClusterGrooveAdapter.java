package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoServer;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;

/**
 * redis 消息处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
public class ClusterGrooveAdapter extends RedisClusterPubSubAdapter {
 
    private final Logger logger = LoggerFactory.getLogger(ClusterGrooveAdapter.class);

    @Autowired
    private DeviceInfoServer deviceInfoServer;
 
    @Override
    public void message(RedisClusterNode node, Object channel, Object message) {

        String channelKey = String.valueOf(channel);
        String redisKey = String.valueOf(message);
        logger.info("RedisClusterListener.message >> receive redis key expire message ,node:{},channel:{},message:{}", node, channelKey, redisKey);
        if (checkChannelAndKey(channelKey, redisKey)) {
            deviceInfoServer.onRedisExpirationMessage(redisKey);
        }
    }


    private  boolean checkChannelAndKey (String channel,String key){
        return StringUtils.equals(channel,EXPIRED_CHANNEL) && StringUtils.startsWith(key,IOT_SESSION_CACHE_KEY_PREFIX);
    }


}