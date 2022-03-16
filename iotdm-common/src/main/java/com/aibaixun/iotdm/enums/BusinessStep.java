package com.aibaixun.iotdm.enums;

/**
 * 业务步骤
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public enum BusinessStep {
    /**
     * 设备上报数据
     */
    DEVICE_REPORT_DATA,

    /**
     * 平台解析数据
     */
    PLATFORM_RESOLVING_DATA,

    /**
     * 平台解析数据 失败
     */
    PLATFORM_RESOLVING_DATA_ERROR,

    /**
     * 平台解析数据 成功
     */
    PLATFORM_RESOLVING_DATA_SUCCESS,

    /**
     * 匹配物模型
     */
    MATCH_MODEL;
}
