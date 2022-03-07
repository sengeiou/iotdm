package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.entity.DeviceTrace;
import com.aibaixun.iotdm.entity.MessageTrace;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IDeviceTraceService;
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

    @PostMapping("/debug")
    public JsonResult<Boolean>  debugDevice(@RequestBody @Valid DeviceTrace deviceTrace) throws BaseException {
        String deviceId = deviceTrace.getDeviceId();
        Boolean traceDebug = deviceTrace.getTraceDebug();
        Device device = deviceService.getById(deviceId);
        if (Objects.isNull(device)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceTraceService.debugDevice(deviceId, traceDebug);
        return JsonResult.success(aBoolean);
    }

    @PostMapping
    public JsonResult<Boolean> traceDevice(@RequestBody @Valid DeviceTrace deviceTrace) throws BaseException {
        String deviceId = deviceTrace.getDeviceId();
        Device device = deviceService.getById(deviceId);
        if (Objects.isNull(device)){
            throw new BaseException("设备信息不存在，无法在线调试", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceTraceService.traceDevice(deviceTrace);
        return JsonResult.success(aBoolean);
    }




    @GetMapping("/{deviceId}/{ts}")
    public JsonResult<List<MessageTrace>> getDeviceTrace (@PathVariable String deviceId,
                                                          @PathVariable Long ts){
        return null;
    }


    @Autowired
    public void setDeviceTraceService(IDeviceTraceService deviceTraceService) {
        this.deviceTraceService = deviceTraceService;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
