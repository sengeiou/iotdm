package com.aibaixun.iotdm.enums;

/**
 * 业务步骤
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public enum BusinessStep {

    /**
     * 设备session
     */
    DEVICE_SESSION,
    /**
     * 设备上报数据
     */
    DEVICE_REPORT_DATA,

    /**
     * 平台解析数据
     */
    PLATFORM_RESOLVING_DATA,

    /**
     * 匹配物模型
     */
    MATCH_MODEL,

    /**
     * 平台发送命令
     */
    PLATFORM_SEND_COMMAND,
    /**
     * 平台发送数据
     */
    PLATFORM_SEND_DATA;
}
