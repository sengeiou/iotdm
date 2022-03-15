package com.aibaixun.iotdm.redis;

import com.aibaixun.iotdm.service.DeviceInfoServer;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;

/**
 * redis key 监听器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface RedisKeyExpirationListener {



    /**
     * 校验channel 与 key
     * @param channel channel
     * @param key key
     * @return 是否与订阅一致
     */
    default  boolean checkChannelAndKey (String channel,String key){
        return StringUtils.equals(channel,EXPIRED_CHANNEL) && StringUtils.startsWith(key,IOT_SESSION_CACHE_KEY_PREFIX);
    }

    /**
     * 过期时间业务逻辑
     * @param redisKey
     */
    default void  doExpirationMessage(String redisKey){
        String[] splitStr = redisKey.split(":");
        int keyMinLength = 4;
        if (splitStr.length!= keyMinLength){
            return;
        }
        String deviceId = splitStr[3];
        getDeviceInfoService().setDeviceStatus2OffOnLine(deviceId,null, Instant.now().toEpochMilli()-1000*60,null);
    }


    /**
     * 设置设备服务类
     * @param deviceInfoService 设备服务类
     */
     void setDeviceInfoService(DeviceInfoServer deviceInfoService);

    /**
     * 获取设备服务类
     * @return 设备服务类
     */
    DeviceInfoServer getDeviceInfoService();
}
