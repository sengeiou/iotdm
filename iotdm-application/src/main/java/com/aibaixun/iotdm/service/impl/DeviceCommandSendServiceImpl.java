package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceCommandSendEntity;
import com.aibaixun.iotdm.mapper.DeviceCommandSendMapper;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 命令下发记录 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceCommandSendServiceImpl extends ServiceImpl<DeviceCommandSendMapper, DeviceCommandSendEntity> implements IDeviceCommandSendService {


    @Override
    public List<DeviceCommandSendEntity> queryDeviceCommandSend(String deviceId, String commandLabel, String commandId, Long startTs, Long endTs, Integer limit) {

        if (Objects.isNull(limit)){
            limit =10;
        }
        String tenantId = UserInfoUtil.getTenantIdOfNull();
        LambdaQueryWrapper<DeviceCommandSendEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DeviceCommandSendEntity::getTenantId,tenantId);
        if (StringUtils.isNotBlank(deviceId)){
            queryWrapper.eq(DeviceCommandSendEntity::getDeviceId,deviceId);
        }
        if (StringUtils.isNotBlank(commandId)){
            queryWrapper.eq(DeviceCommandSendEntity::getCommandId,commandId);
        }
        if (StringUtils.isNotBlank(commandLabel)){
            queryWrapper.likeRight(DeviceCommandSendEntity::getCommandLabel,commandLabel);
        }
        if (Objects.nonNull(startTs) && Objects.nonNull(endTs)){
            queryWrapper.between(DeviceCommandSendEntity::getRespTs,startTs,endTs);
        }
        queryWrapper.orderByDesc(DeviceCommandSendEntity::getRespTs);
        queryWrapper.last(" LIMIT "+limit);
        return list(queryWrapper);
    }
}
