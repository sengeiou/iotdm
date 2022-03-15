package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoServer;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;



/**
 * 集群版本 监听
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
@ConditionalOnExpression("!'${spring.redis.sub.cluster:}'.isEmpty()")
public class RedisClusterListener extends RedisClusterPubSubAdapter<String,String> implements RedisKeyExpirationListener {

    private final Logger log = LoggerFactory.getLogger(RedisClusterListener.class);

    private DeviceInfoServer deviceInfoService;

    @Override
    public void message(RedisClusterNode node, String pattern, String channel, String message) {
        log.info("RedisClusterListener.message >> receive redis key expire message ,node:{},channel:{},message:{}", node, channel, message);
        if (checkChannelAndKey(channel, channel)) {
            doExpirationMessage(message);
        }
    }


    @Autowired
    @Override
    public void setDeviceInfoService(DeviceInfoServer deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Override
    public DeviceInfoServer getDeviceInfoService() {
        return deviceInfoService;
    }
}
