package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceGroupEntity;
import com.aibaixun.iotdm.mapper.DeviceGroupMapper;
import com.aibaixun.iotdm.service.IDeviceGroupService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备分组 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceGroupServiceImpl extends ServiceImpl<DeviceGroupMapper, DeviceGroupEntity> implements IDeviceGroupService {

    @Override
    public List<DeviceGroupEntity> listQueryDeviceGroup(Integer limit) {
        LambdaQueryWrapper<DeviceGroupEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DeviceGroupEntity::getTenantId, UserInfoUtil.getTenantIdOfNull());
        queryWrapper.last(" LIMIT "+limit);
        return list(queryWrapper);
    }


    @Override
    public Long countSubGroup(String superGroupId) {
        return count(Wrappers.<DeviceGroupEntity>lambdaQuery().eq(DeviceGroupEntity::getSuperGroupId,superGroupId));
    }
}
