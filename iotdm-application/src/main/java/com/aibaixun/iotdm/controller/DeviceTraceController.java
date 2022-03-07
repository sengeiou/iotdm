package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.DeviceTraceEntity;
import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IDeviceTraceService;
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



    private IDeviceTraceService deviceTraceService;


    private IDeviceService deviceService;

    private IMessageTraceService messageTraceService;

    @PostMapping("/debug")
    public JsonResult<Boolean>  debugDevice(@RequestBody @Valid DeviceTraceEntity deviceTraceEntity) throws BaseException {
        String deviceId = deviceTraceEntity.getDeviceId();
        Boolean traceDebug = deviceTraceEntity.getTraceDebug();
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceTraceService.debugDevice(deviceId, traceDebug);
        return JsonResult.success(aBoolean);
    }

    @PostMapping
    public JsonResult<Boolean> traceDevice(@RequestBody @Valid DeviceTraceEntity deviceTraceEntity) throws BaseException {
        String deviceId = deviceTraceEntity.getDeviceId();
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceTraceService.traceDevice(deviceTraceEntity);
        return JsonResult.success(aBoolean);
    }




    @GetMapping("/{deviceId}/{ts}")
    public JsonResult<List<MessageTraceEntity>> getDeviceTrace (@PathVariable String deviceId,
                                                                @PathVariable Long ts){
        List<MessageTraceEntity> messageTraceEntities = messageTraceService.queryMessageTrace(deviceId, ts);
        return JsonResult.success(messageTraceEntities);
    }


    @Autowired
    public void setDeviceTraceService(IDeviceTraceService deviceTraceService) {
        this.deviceTraceService = deviceTraceService;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setMessageTraceService(IMessageTraceService messageTraceService) {
        this.messageTraceService = messageTraceService;
    }
}
