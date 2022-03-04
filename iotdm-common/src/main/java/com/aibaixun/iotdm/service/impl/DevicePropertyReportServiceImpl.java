package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DevicePropertyReport;
import com.aibaixun.iotdm.mapper.DevicePropertyReportMapper;
import com.aibaixun.iotdm.service.IDevicePropertyReportService;
import com.aibaixun.iotdm.support.DevicePropertyInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class DevicePropertyReportServiceImpl extends ServiceImpl<DevicePropertyReportMapper, DevicePropertyReport> implements IDevicePropertyReportService {


    @Override
    public List<DevicePropertyInfo> queryLatestDeviceProperty(String deviceId) {
        return baseMapper.selectLatestDeviceProperty(deviceId);
    }


    @Override
    public List<DevicePropertyInfo> queryShadowDeviceProperty(String deviceId) {
        return baseMapper.selectShadowDeviceProperty(deviceId);
    }
}
