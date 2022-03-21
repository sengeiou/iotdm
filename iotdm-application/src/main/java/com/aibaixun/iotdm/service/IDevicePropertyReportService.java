package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import com.aibaixun.iotdm.data.DevicePropertyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备属性上报记录表  服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IDevicePropertyReportService extends IService<DevicePropertyReportEntity> {


    /**
     * 查询设备最新 属性上报 数据 【理论上表中只会存在最新的数据】
     * @param deviceId 设备id
     * @return 最新属性上报数据
     */
    List<DevicePropertyInfo> queryLatestDeviceProperty(String deviceId);


    /**
     * 查询 设备影子
     * @param productId 产品id
     * @return 设备属性最新上报数据
     */
    List<DevicePropertyInfo> queryShadowDeviceProperty(String productId,String propertyLabel);

}
