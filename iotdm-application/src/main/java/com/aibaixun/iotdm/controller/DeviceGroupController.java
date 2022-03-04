package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceGroup;
import com.aibaixun.iotdm.service.IDeviceGroupService;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.support.DeviceInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 设备群组 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
@RestController
@RequestMapping("/device-group")
public class DeviceGroupController extends BaseController{


    private IDeviceGroupService deviceGroupService;


    private IDeviceService deviceService;

    @GetMapping("/list")
    public JsonResult<List<DeviceGroup>> listQueryDeviceGroup (@RequestParam(required = false) Integer limit) {
        if (Objects.isNull(limit)){
            limit = 200;
        }
        List<DeviceGroup> deviceGroups = deviceGroupService.listQueryDeviceGroup(limit);
        return JsonResult.success(deviceGroups);
    }



    @GetMapping("/{id}")
    public JsonResult<DeviceGroup> queryDeviceGroup (@PathVariable String id){
        DeviceGroup deviceGroup = deviceGroupService.getById(id);
        return JsonResult.success(deviceGroup);
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeDeviceGroup (@PathVariable String id) throws BaseException {
        DeviceGroup deviceGroup = deviceGroupService.getById(id);
        if (Objects.isNull(deviceGroup)){
            throw new BaseException("设备分组不存在无法删除", BaseResultCode.BAD_PARAMS);
        }
        Long countSubGroup = deviceGroupService.countSubGroup(id);
        if (countSubGroup>0){
            throw new BaseException("该群组下面包含子群组,请删除后再重试", BaseResultCode.BAD_PARAMS);
        }
        boolean b = deviceGroupService.removeById(id);
        return JsonResult.success(b);
    }


    @PostMapping
    public JsonResult<Boolean> createDeviceGroup (@RequestBody @Valid DeviceGroup deviceGroup){
        boolean save = deviceGroupService.save(deviceGroup);
        return JsonResult.success(save);
    }



    @GetMapping("/devices")
    public JsonResult<Page<DeviceInfo>> pageQueryDeviceByGroup (@RequestParam Integer  page,
                                                                @RequestParam Integer pageSize,
                                                                @RequestParam(required = false) String groupId,
                                                                @RequestParam(required = false) String productId,
                                                                @RequestParam(required = false) String deviceCode,
                                                                @RequestParam(required = false) String deviceLabel) throws BaseException {

        checkPage(page,pageSize);
        Page<DeviceInfo> deviceInfoPage = deviceService.pageQueryDeviceByGroup(page, pageSize, groupId, productId, deviceCode, deviceLabel);
        return JsonResult.success(deviceInfoPage);
    }





    @Autowired
    public void setDeviceGroupService(IDeviceGroupService deviceGroupService) {
        this.deviceGroupService = deviceGroupService;
    }
}
