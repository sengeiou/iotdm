package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceCommandSend;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备命令 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("device-command")
public class DeviceCommandController {


    private IDeviceCommandSendService deviceCommandSendService;


    @GetMapping("/list")
    public JsonResult<DeviceCommandSend> queryDeviceCommands (@RequestParam(required = false) String commandLabel) {

        return null;
    }

    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }
}
