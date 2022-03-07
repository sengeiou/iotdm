package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.DeviceTrace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备追踪 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface DeviceTraceMapper extends BaseMapper<DeviceTrace> {


    /**
     * 设备 debug 开启
     * @param deviceId 设备id
     * @param traceDebug debug
     * @param userId 用户id
     * @param tenantId 租户id
     * @return 更改记录
     */
    Long updateDeviceDebug(@Param("deviceId") String deviceId,@Param("traceDebug") Integer traceDebug,@Param("userId") String userId,@Param("tenantId") String tenantId);
}
