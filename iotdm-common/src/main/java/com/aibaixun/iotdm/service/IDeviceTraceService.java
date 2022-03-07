package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DeviceTrace;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 设备追踪 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IDeviceTraceService extends IService<DeviceTrace> {

    /**
     * 设备调试
     * @param deviceId 设备id
     * @param traceDebug debug
     * @return 更改结果
     */
    Boolean debugDevice (String deviceId,Boolean traceDebug);

    /**
     * 设备追踪
     * @param deviceTrace 设备追踪
     * @return 更改结果
     */
    Boolean traceDevice(DeviceTrace deviceTrace);

}
