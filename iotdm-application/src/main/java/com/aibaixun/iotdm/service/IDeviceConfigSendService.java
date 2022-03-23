package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DeviceConfigSendEntity;
import com.aibaixun.iotdm.enums.SendStatus;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public interface IDeviceConfigSendService extends IService<DeviceConfigSendEntity> {

    /**
     * 保存 或者更新 确保只有一条数据
     * @param deviceConfigSendEntity 设备信息
     */
     void saveOrUpdateConfigSend(DeviceConfigSendEntity deviceConfigSendEntity);

    /**
     * 查询发送结果
     * @param deviceId 设备id
     * @return 设备发送信息
     */
     DeviceConfigSendEntity queryDeviceConfigSendByDeviceId(String deviceId);


    /**
     * 更改状态
     * @param deviceId 设备id
     * @param sendStatus 发送结果
     * @return 更改结果
     */
    Boolean updateDeviceConfigSend(String deviceId, SendStatus sendStatus);

}
