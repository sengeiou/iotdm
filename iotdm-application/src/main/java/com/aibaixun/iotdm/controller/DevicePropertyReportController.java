package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.service.IDevicePropertyReportService;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.data.DevicePropertyInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 设备属性 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
@RestController
@RequestMapping("/device-property")
public class DevicePropertyReportController extends BaseController{


    private IDevicePropertyReportService devicePropertyReportService;

    private IDeviceService deviceService;

    @GetMapping("/latest")
    public JsonResult<List<DevicePropertyInfo>> queryLatestDeviceProperty(@RequestParam String deviceId) throws BaseException {
        if (StringUtils.isBlank(deviceId)){
            throw new BaseException("设备id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备已经被删除",BaseResultCode.BAD_PARAMS);
        }
        List<DevicePropertyInfo> devicePropertyInfos = devicePropertyReportService.queryLatestDeviceProperty(deviceId);
        return JsonResult.success(devicePropertyInfos);
    }


    @GetMapping("/shadow")
    public JsonResult<List<DevicePropertyInfo>> queryShadowDeviceProperty(@RequestParam String deviceId) throws BaseException {
        if (StringUtils.isBlank(deviceId)){
            throw new BaseException("设备id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备已经被删除",BaseResultCode.BAD_PARAMS);
        }
        List<DevicePropertyInfo> devicePropertyInfos = devicePropertyReportService.queryShadowDeviceProperty(deviceId);
        return JsonResult.success(devicePropertyInfos);
    }





    @Autowired
    public void setDevicePropertyReportService(IDevicePropertyReportService devicePropertyReportService) {
        this.devicePropertyReportService = devicePropertyReportService;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
