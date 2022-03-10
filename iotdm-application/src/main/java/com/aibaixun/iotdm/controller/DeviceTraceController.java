package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IMessageTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        redisRepository.setExpire(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY+deviceId,60,60 );
        return JsonResult.success(true);
    }

    @GetMapping("/{deviceId}")
    public JsonResult<Boolean> traceDevice(@PathVariable String deviceId,@RequestParam Long ttl) throws BaseException {

        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        long threeDay = 259200;
        if (ttl> threeDay){
            throw new BaseException("最多可以追踪3天消息", BaseResultCode.GENERAL_ERROR);
        }
        redisRepository.setExpire(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY+deviceId,ttl,ttl );
        return JsonResult.success(true);
    }


    @GetMapping
    public JsonResult<Long> tranceDevice (@RequestParam String deviceId){
        Long ttl = ((Long) redisRepository.get(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY + deviceId));
        if (ttl >60){
            return JsonResult.success(ttl);
        }
        return JsonResult.success(null);
    }




    @GetMapping("/{deviceId}/{ts}")
    public JsonResult<List<MessageTraceEntity>> getDeviceTrace (@PathVariable String deviceId,
                                                                @PathVariable Long ts){
        List<MessageTraceEntity> messageTraceEntities = messageTraceService.queryMessageTrace(deviceId, ts);
        Long ttl = ((Long) redisRepository.get(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY + deviceId));
        if (Objects.isNull(ttl)){
            redisRepository.setExpire(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY+deviceId,60,60 );
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
