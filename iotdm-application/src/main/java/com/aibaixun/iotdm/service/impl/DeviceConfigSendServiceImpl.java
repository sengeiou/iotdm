package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceConfigSendEntity;
import com.aibaixun.iotdm.enums.SendStatus;
import com.aibaixun.iotdm.mapper.DeviceConfigSendEntityMapper;
import com.aibaixun.iotdm.service.IDeviceConfigSendService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
@Service
public class DeviceConfigSendServiceImpl extends ServiceImpl<DeviceConfigSendEntityMapper, DeviceConfigSendEntity> implements IDeviceConfigSendService {

    @Override
    public void saveOrUpdateConfigSend(DeviceConfigSendEntity deviceConfigSendEntity) {
         deviceConfigSendEntity.setTenantId(UserInfoUtil.getTenantIdOfNull());
         baseMapper.saveOrUpdateConfigSend(deviceConfigSendEntity);
    }

    @Override
    public DeviceConfigSendEntity queryDeviceConfigSendByDeviceId(String deviceId) {
        return getOne(Wrappers.<DeviceConfigSendEntity>lambdaQuery().eq(DeviceConfigSendEntity::getDeviceId,deviceId),false);
    }

    @Override
    public Boolean updateDeviceConfigSend(String deviceId, SendStatus sendStatus) {
        return update(Wrappers.<DeviceConfigSendEntity>lambdaUpdate().eq(DeviceConfigSendEntity::getDeviceId,deviceId)
                .set(DeviceConfigSendEntity::getSendStatus,sendStatus));
    }
}
