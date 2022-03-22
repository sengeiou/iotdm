package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoServer;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;


/**
 * 集群版本 监听
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
@ConditionalOnExpression("!'${spring.redis.sub.cluster:}'.isEmpty()")
public class RedisClusterListener extends RedisClusterPubSubAdapter  {

    private final Logger log = LoggerFactory.getLogger(RedisClusterListener.class);

    private DeviceInfoServer deviceInfoService;




    @Override
    public void message(RedisClusterNode node, Object pattern, Object channel, Object message) {
        log.info("RedisClusterListener.message >> receive redis key expire message ,node:{},channel:{},message:{}", node, channel, message);
        if (checkChannelAndKey(String.valueOf(pattern), String.valueOf(message))) {
            doExpirationMessage(String.valueOf(message));
        }
    }

    private   boolean checkChannelAndKey (String channel, String key){
        return StringUtils.equals(channel,EXPIRED_CHANNEL) && StringUtils.startsWith(key,IOT_SESSION_CACHE_KEY_PREFIX);
    }

    /**
     * 过期时间业务逻辑
     * @param redisKey redis key
     */
    private void  doExpirationMessage(String redisKey){
        String[] splitStr = redisKey.split(":");
        int keyMinLength = 4;
        if (splitStr.length!= keyMinLength){
            return;
        }
        String deviceId = splitStr[3];
        deviceInfoService.setDeviceStatus2OffOnLine(deviceId,null, Instant.now().toEpochMilli()-1000*60,null);
    }

    @Autowired
    public void setDeviceInfoService(DeviceInfoServer deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @PostConstruct
    public void init () {
        System.out.println("111");
    }




}
