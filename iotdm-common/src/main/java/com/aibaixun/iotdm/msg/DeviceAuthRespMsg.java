package com.aibaixun.iotdm.msg;

import java.io.Serializable;
import java.util.Objects;

/**
 * 设备认证 结果
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DeviceAuthRespMsg implements Serializable {

    /**
     * 设备信息
     */
    private DeviceInfo deviceInfo;


    public boolean hasDeviceInfo () {
        return Objects.nonNull(deviceInfo);
    }


    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

}
