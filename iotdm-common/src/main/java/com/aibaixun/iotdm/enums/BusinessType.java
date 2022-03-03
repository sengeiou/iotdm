package com.aibaixun.iotdm.enums;

/**
 * 业务类型
 * 对传递的数据做了大类的区分
 * <p>设备到平台，平台到设备，平台转发</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public enum BusinessType {

    /**
     * 设备到平台
     */
    DEVICE2PLATFORM,
    /**
     * 平台到设备
     */
    PLATFORM2DEVICE,
    /**
     * 平台转发
     */
    PLATFORM_FORWARD;

}
