package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;


/**
 * 单节点监听器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Component
@ConditionalOnBean(value = RedisMessageListenerContainer.class)
@ConditionalOnExpression("'${spring.redis.sub.cluster:}'.isEmpty() && !'${spring.redis.host:}'.isEmpty()")
public class RedisStandAloneListener extends KeyExpirationEventMessageListener {

    private final Logger log  = LoggerFactory.getLogger(RedisClusterListener.class);

    private DeviceInfoServer deviceInfoService;

    public RedisStandAloneListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String key = new String(message.getBody(),StandardCharsets.UTF_8);
        log.info("RedisStandAloneListener.onMessage >> receive redis key expire message ,channel:{},message:{}",channel,message);
        if (checkChannelAndKey(channel,key)){
            deviceInfoService.onRedisExpirationMessage(String.valueOf(message));
        }
    }


    /**
     * 校验channel 与 key
     * @param channel channel
     * @param key key
     * @return 是否与订阅一致
     */
    private   boolean checkChannelAndKey (String channel,String key){
        return StringUtils.equals(channel,EXPIRED_CHANNEL) && StringUtils.startsWith(key,IOT_SESSION_CACHE_KEY_PREFIX);
    }




    @Autowired
    public void setDeviceInfoService(DeviceInfoServer deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }



}
