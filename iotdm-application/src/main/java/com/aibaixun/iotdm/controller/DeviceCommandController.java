package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceCommandSendEntity;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 设备命令 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("device-command")
public class DeviceCommandController extends BaseController{


    private IDeviceCommandSendService deviceCommandSendService;


    @GetMapping("/page")
    public JsonResult<Page<DeviceCommandSendEntity>> queryDeviceCommands (
                                                              @RequestParam(required = false)  String deviceId,
                                                              @RequestParam(required = false) String commandLabel,
                                                              @RequestParam(required = false) String commandId,
                                                              @RequestParam(required = false) Long startTs,
                                                              @RequestParam(required = false) Long endTs,
                                                              @RequestParam(required = false) Integer page,
                                                              @RequestParam(required = false) Integer pageSize) throws BaseException {

        checkPage(page, pageSize);

        Page<DeviceCommandSendEntity> deviceCommandSendEntities = deviceCommandSendService.pageQueryDeviceCommandSend(deviceId,
                commandLabel, commandId, startTs, endTs, page,pageSize);
        return JsonResult.success(deviceCommandSendEntities);
    }






    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }



}
