package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import com.aibaixun.iotdm.data.DevicePropertyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备属性上报记录表  Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface DevicePropertyReportMapper extends BaseMapper<DevicePropertyReportEntity> {


    /**
     * 查询设备上报最新数据 理论上该表只会存在 一条最新数据
     * @param deviceId 设备id
     * @return 最新上报数据
     */
    List<DevicePropertyInfo> selectLatestDeviceProperty(@Param("deviceId") String deviceId);

    /**
     * 设备影子数据查询
     * @param deviceId 设备id
     * @return 最新设备属性信息
     */
    List<DevicePropertyInfo> selectShadowDeviceProperty(@Param("deviceId") String deviceId);
}
