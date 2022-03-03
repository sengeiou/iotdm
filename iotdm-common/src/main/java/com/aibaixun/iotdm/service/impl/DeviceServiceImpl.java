package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.mapper.DeviceMapper;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.support.DeviceInfo;
import com.aibaixun.iotdm.support.KvData;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {


    @Override
    public Device queryById(String id) {
        Device device = getById(id);
        device.setTenantId(null);
        device.setLastConnectTs(null);
        device.setCreator(null);
        return device;
    }

    @Override
    public List<KvData<Long>> countDevice(String productId) {
        String tenantId = UserInfoUtil.getTenantIdOfNull();
        return baseMapper.countDevice(productId, tenantId);
    }

    @Override
    public List<Device> queryDevice(String productId) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Device::getId,Device::getDeviceLabel,Device::getDeviceCode,Device::getVirtual);
        if (StringUtils.isNotBlank(productId)){
            queryWrapper.eq(Device::getProductId,productId);
        }
        return list(queryWrapper);
    }




    @Override
    public Page<DeviceInfo> pageQueryDeviceInfos(Integer page, Integer pageSize, DeviceStatus deviceStatus, String searchKey, String searchValue) {
        return baseMapper.selectPageDeviceInfo(Page.of(page,pageSize),UserInfoUtil.getTenantIdOfNull(),deviceStatus,searchKey,searchValue);
    }
}
