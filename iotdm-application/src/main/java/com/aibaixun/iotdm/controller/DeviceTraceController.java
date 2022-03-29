package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.data.TraceDeviceParam;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IMessageTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.aibaixun.iotdm.constants.DataConstants.DEFAULT_DEVICE_DEBUG_TTL;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@RestController
@RequestMapping("/device-trace")
public class DeviceTraceController extends BaseController{

    private RedisRepository redisRepository;

    private IDeviceService deviceService;

    private IMessageTraceService messageTraceService;



    @GetMapping("/debug/{deviceId}")
    public JsonResult<Boolean>  debugDevice(@PathVariable String deviceId) throws BaseException {
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        checkEntity(deviceEntity,"设备信息不存在，无法在线调试");
        long ttl = Instant.now().toEpochMilli() + DEFAULT_DEVICE_DEBUG_TTL;
        redisRepository.putHashValue(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY,deviceId,ttl );
        redisRepository.getRedisTemplate().expire(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY,4, TimeUnit.DAYS);
        return JsonResult.success(true);
    }

    @PostMapping
    public JsonResult<Boolean> getTraceDeviceTime(@RequestBody TraceDeviceParam traceDeviceParam) throws BaseException {

        Long ttl = traceDeviceParam.getTtl();
        DeviceEntity deviceEntity = deviceService.getById(traceDeviceParam.getDeviceId());
        checkEntity(deviceEntity,"设备信息不存在，无法获取消息追踪时间");
        long value = Instant.now().toEpochMilli() + ttl*1000;
        redisRepository.putHashValue(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY,traceDeviceParam.getDeviceId(),value );
        redisRepository.getRedisTemplate().expire(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY,4, TimeUnit.DAYS);
        return JsonResult.success(true);
    }


    @GetMapping("/time/{deviceId}")
    public JsonResult<Long> getTraceDeviceTime(@PathVariable String deviceId){
        Long ttl = ((Long) redisRepository.getHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId));
        return JsonResult.success(ttl);
    }

    @DeleteMapping("/{deviceId}")
    public JsonResult<Boolean> removeTraceDevice (@PathVariable String deviceId){
        redisRepository.delHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId);
        return JsonResult.success(true);
    }




    @GetMapping("/{deviceId}")
    public JsonResult<List<MessageTraceEntity>> getDeviceTraceMessage (@PathVariable String deviceId,
                                                                       @RequestParam(required = false) Boolean messageStatus,
                                                                       @RequestParam(required = false) BusinessType businessType,
                                                                       @RequestParam(required = false) Boolean debugDevice) {
        if (Objects.isNull(debugDevice)){
            debugDevice = false;
        }
        List<MessageTraceEntity> messageTraceEntities = messageTraceService.queryMessageTrace(deviceId, businessType,messageStatus,debugDevice);
        Long diffTtl = ((Long) redisRepository.getHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId));
        if (Objects.isNull(diffTtl) || diffTtl < Instant.now().toEpochMilli()){
            long value = Instant.now().toEpochMilli() + 10000;
            redisRepository.putHashValue(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY,deviceId,value);
        }
        return JsonResult.success(messageTraceEntities);
    }




    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setMessageTraceService(IMessageTraceService messageTraceService) {
        this.messageTraceService = messageTraceService;
    }

    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
