package com.aibaixun.iotdm.support;

import java.io.Serializable;

/**
 * 发送到设备的基础数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public abstract class ToDeviceBaseData implements Serializable {

    private final ToDeviceType toDeviceType;

    public ToDeviceBaseData(ToDeviceType toDeviceType) {
        this.toDeviceType = toDeviceType;
    }

    public ToDeviceType getToDeviceType() {
        return toDeviceType;
    }

}
