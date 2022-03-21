package com.aibaixun.iotdm.server;

import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.service.IMessageTraceService;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

/**
 * 设备日志记录器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@Component
public class DeviceLogProcessor {

    private RedisRepository redisRepository;

    private IMessageTraceService messageTraceService;

    public  void doDevice2PlatformLog(String deviceId,  BusinessStep businessStep, String businessDetails, Boolean messageStatus  ) {
      doLog(deviceId, businessStep,businessDetails,messageStatus);
    }


    public  void doPlatform2DeviceLLog(String deviceId,  BusinessStep businessStep, String businessDetails, Boolean messageStatus ) {
        doLog(deviceId, businessStep,businessDetails,messageStatus);
    }

    private void doLog(String deviceId, BusinessStep businessStep, String businessDetails, Boolean messageStatus){
        Long ttl = ((Long) redisRepository.getHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId));
        if (Objects.nonNull(ttl) && ttl > Instant.now().getEpochSecond()){
            Futures.submit(()-> messageTraceService.logDeviceMessageTrace(deviceId,BusinessType.DEVICE2PLATFORM,businessStep,businessDetails,messageStatus), MoreExecutors.directExecutor());
        }
    }

    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
    @Autowired
    public void setMessageTraceService(IMessageTraceService messageTraceService) {
        this.messageTraceService = messageTraceService;
    }
}
