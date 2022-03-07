package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceCommandSendEntity;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 设备命令 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("device-command")
public class DeviceCommandController extends BaseController{


    private IDeviceCommandSendService deviceCommandSendService;


    @GetMapping("/list")
    public JsonResult<List<DeviceCommandSendEntity>> queryDeviceCommands (
                                                              @RequestParam(required = false)  String deviceId,
                                                              @RequestParam(required = false) String commandLabel,
                                                              @RequestParam(required = false) String commandId,
                                                              @RequestParam(required = false) Long startTs,
                                                              @RequestParam(required = false) Long endTs,
                                                              @RequestParam(required = false) Integer limit) {

        List<DeviceCommandSendEntity> deviceCommandSendEntities = deviceCommandSendService.queryDeviceCommandSend(deviceId,commandLabel, commandId, startTs, endTs, limit);
        return JsonResult.success(deviceCommandSendEntities);
    }

    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }



}
