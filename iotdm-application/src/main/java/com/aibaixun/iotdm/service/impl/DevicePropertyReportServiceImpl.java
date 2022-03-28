package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import com.aibaixun.iotdm.mapper.DevicePropertyReportMapper;
import com.aibaixun.iotdm.service.IDevicePropertyReportService;
import com.aibaixun.iotdm.data.DevicePropertyInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 设备属性上报记录表  服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DevicePropertyReportServiceImpl extends ServiceImpl<DevicePropertyReportMapper, DevicePropertyReportEntity> implements IDevicePropertyReportService {


    @Override
    @Cacheable(cacheNames = "IOTDM:DEVICE_LATEST:",key = "#deviceId",unless = "#result == null ")
    public List<DevicePropertyInfo> queryLatestDeviceProperty(String deviceId) {
        return baseMapper.selectLatestDeviceProperty(deviceId);
    }


    @Override
    public List<DevicePropertyInfo> queryShadowDeviceProperty(String productId,String propertyLabel,String deviceId) {
        return baseMapper.selectShadowDeviceProperty(productId,propertyLabel,deviceId);
    }

    @Override
    @CacheEvict(cacheNames = "IOTDM:DEVICE_LATEST:",key = "#deviceId")
    public void saveOrUpdateBatchDeviceProperties(String deviceId, Collection<DevicePropertyReportEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)){
            return;
        }
        int size = entityList.size();
        int i = baseMapper.saveOrUpdateBatch(new ArrayList<>(entityList));
    }
}
