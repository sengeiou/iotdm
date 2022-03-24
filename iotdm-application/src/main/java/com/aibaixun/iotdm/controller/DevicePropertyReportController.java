package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.DevicePropertyInfo;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.service.IDevicePropertyReportService;
import com.aibaixun.iotdm.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        checkParameterValue(deviceId,"设备id不允许为空");
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        checkEntity(deviceEntity,"设备已经被删除,无法查询最新数据");
        List<DevicePropertyInfo> devicePropertyInfos = devicePropertyReportService.queryLatestDeviceProperty(deviceId);
        return JsonResult.success(devicePropertyInfos);
    }


    @GetMapping("/shadow")
    public JsonResult<List<DevicePropertyInfo>> queryShadowDeviceProperty(@RequestParam String deviceId,
                                                                          @RequestParam(required = false) String propertyLabel) throws BaseException {
        checkParameterValue(deviceId,"设备id不允许为空");
        DeviceEntity deviceEntity = deviceService.getById(deviceId);
        checkEntity(deviceEntity,"设备已经被删除,无法查询最新数据");
        List<DevicePropertyInfo> devicePropertyInfos = devicePropertyReportService.queryShadowDeviceProperty(deviceEntity.getProductId(),propertyLabel);
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
