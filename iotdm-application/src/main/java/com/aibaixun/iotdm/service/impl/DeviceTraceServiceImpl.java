package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceTraceEntity;
import com.aibaixun.iotdm.mapper.DeviceTraceMapper;
import com.aibaixun.iotdm.service.IDeviceTraceService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备追踪 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceTraceServiceImpl extends ServiceImpl<DeviceTraceMapper, DeviceTraceEntity> implements IDeviceTraceService {

    @Override
    public Boolean debugDevice(String deviceId,Boolean traceDebug) {
        Long num = baseMapper.updateDeviceDebug(deviceId, traceDebug ? 1 : 0, UserInfoUtil.getUserIdOfNull(), UserInfoUtil.getTenantIdOfNull());
        return num>0;
    }

    @Override
    public Boolean traceDevice(DeviceTraceEntity deviceTraceEntity) {
        return saveOrUpdate(deviceTraceEntity);
    }
}
