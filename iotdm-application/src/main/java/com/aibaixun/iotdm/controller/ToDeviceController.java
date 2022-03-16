package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.ToDeviceCommandParam;
import com.aibaixun.iotdm.data.ToDeviceConfigParam;
import com.aibaixun.iotdm.data.ToDeviceOtaParam;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.server.ToDeviceProcessor;
import com.aibaixun.iotdm.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@RestController
@RequestMapping("/2device")
public class ToDeviceController extends BaseController{


    private ToDeviceProcessor toDeviceProcessor;

    private IDeviceService deviceService;

    @PostMapping("/command")
    public JsonResult<Boolean> sendCommandToDevice(@RequestBody @Valid ToDeviceCommandParam deviceCommandParam){
        String deviceId = deviceCommandParam.getDeviceId();
        DeviceEntity deviceEntity = getById(deviceId);
        if (checkDevice(deviceEntity)){
            return JsonResult.failed("设备不存在或者设备不在线");
        }
        toDeviceProcessor.processControl(deviceId,deviceEntity.getProductId(),deviceCommandParam.getCommandId(),deviceCommandParam.getParams());
        return JsonResult.successJustMsg("设备命令已经发送，但不代表会执行成功");
    }


    @PostMapping("/config")
    public JsonResult<Boolean> sendConfigToDevice(@RequestBody @Valid ToDeviceConfigParam deviceConfigParam){
        String deviceId = deviceConfigParam.getDeviceId();
        DeviceEntity deviceEntity = getById(deviceId);
        if (checkDevice(deviceEntity)){
            return JsonResult.failed("设备不存在或者设备不在线");
        }
        toDeviceProcessor.processConfig(deviceId,deviceEntity.getProductId(),deviceConfigParam.getHost(),deviceConfigParam.getPort());
        return JsonResult.successJustMsg("设备命令已经发送，但不代表会执行成功");
    }


    @PostMapping("/ota")
    public JsonResult<Boolean> sendOtaToDevice(@RequestBody @Valid ToDeviceOtaParam deviceOtaParam){
        String deviceId = deviceOtaParam.getDeviceId();
        DeviceEntity deviceEntity = getById(deviceId);
        if (checkDevice(deviceEntity)){
            return JsonResult.failed("设备不存在或者设备不在线");
        }
        toDeviceProcessor.processOta(deviceId,deviceEntity.getProductId(),deviceOtaParam.getOtaId());
        return JsonResult.successJustMsg("设备命令已经发送，但不代表会执行成功");
    }



    /**
     * 监测设备存在 与是否在线
     * @param deviceEntity device
     * @return boolean
     */
    private boolean checkDevice(DeviceEntity deviceEntity){
        return !Objects.nonNull(deviceEntity) || !Objects.equals(DeviceStatus.ONLINE, deviceEntity.getDeviceStatus());
    }

    private DeviceEntity getById(String deviceId){
        return deviceService.getById(deviceId);
    }


    @Autowired
    public void setToDeviceProcessor(ToDeviceProcessor toDeviceProcessor) {
        this.toDeviceProcessor = toDeviceProcessor;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
