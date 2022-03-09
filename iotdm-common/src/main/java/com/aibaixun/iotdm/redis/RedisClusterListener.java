package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoService;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
public class RedisClusterListener extends RedisClusterPubSubAdapter<String,String> {

    private final Logger log  = LoggerFactory.getLogger(RedisClusterListener.class);

    private DeviceInfoService deviceInfoService;

    @Override
    public void message(RedisClusterNode node, String pattern, String channel, String message) {
        log.info("RedisClusterListener.message >> receive redis key expire message ,node:{},channel:{},message:{}",node,channel,message);
        if (StringUtils.equals(channel,EXPIRED_CHANNEL) && StringUtils.startsWith(message,IOT_SESSION_CACHE_KEY_PREFIX)){
            String[] splitStr = message.split(":");
            int keyMinLength = 4;
            if (splitStr.length!= keyMinLength){
                return;
            }
            String sessionId = splitStr[2];
            String deviceId = splitStr[3];
            log.info("RedisClusterListener.message >> Session Cache is expired,cache key:{},sessionId:{} ",message,sessionId);
            deviceInfoService.setDeviceStatus2OffOnLine(deviceId,null, Instant.now().toEpochMilli(),null);
        }
    }


    @Autowired
    public void setDeviceInfoService(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }
}
