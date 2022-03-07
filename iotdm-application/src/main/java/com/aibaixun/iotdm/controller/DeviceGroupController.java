package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceGroupEntity;
import com.aibaixun.iotdm.service.IDeviceGroupService;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.data.DeviceEntityInfo;
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
    public JsonResult<List<DeviceGroupEntity>> listQueryDeviceGroup (@RequestParam(required = false) Integer limit) {
        if (Objects.isNull(limit)){
            limit = 200;
        }
        List<DeviceGroupEntity> deviceGroupEntities = deviceGroupService.listQueryDeviceGroup(limit);
        return JsonResult.success(deviceGroupEntities);
    }



    @GetMapping("/{id}")
    public JsonResult<DeviceGroupEntity> queryDeviceGroup (@PathVariable String id){
        DeviceGroupEntity deviceGroupEntity = deviceGroupService.getById(id);
        return JsonResult.success(deviceGroupEntity);
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeDeviceGroup (@PathVariable String id) throws BaseException {
        DeviceGroupEntity deviceGroupEntity = deviceGroupService.getById(id);
        if (Objects.isNull(deviceGroupEntity)){
            throw new BaseException("设备分组不存在无法删除", BaseResultCode.BAD_PARAMS);
        }
        Long countSubGroup = deviceGroupService.countSubGroup(id);
        if (countSubGroup>0){
            throw new BaseException("该群组下面包含子群组,请删除后再重试", BaseResultCode.GENERAL_ERROR);
        }
        boolean b = deviceGroupService.removeById(id);
        return JsonResult.success(b);
    }


    @PostMapping
    public JsonResult<Boolean> createDeviceGroup (@RequestBody @Valid DeviceGroupEntity deviceGroupEntity){
        boolean save = deviceGroupService.save(deviceGroupEntity);
        return JsonResult.success(save);
    }



    @GetMapping("/devices")
    public JsonResult<Page<DeviceEntityInfo>> pageQueryDeviceByGroup (@RequestParam Integer  page,
                                                                      @RequestParam Integer pageSize,
                                                                      @RequestParam(required = false) String groupId,
                                                                      @RequestParam(required = false) String productId,
                                                                      @RequestParam(required = false) String deviceCode,
                                                                      @RequestParam(required = false) String deviceLabel) throws BaseException {

        checkPage(page,pageSize);
        Page<DeviceEntityInfo> deviceInfoPage = deviceService.pageQueryDeviceByGroup(page, pageSize, groupId, productId, deviceCode, deviceLabel);
        return JsonResult.success(deviceInfoPage);
    }





    @Autowired
    public void setDeviceGroupService(IDeviceGroupService deviceGroupService) {
        this.deviceGroupService = deviceGroupService;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
