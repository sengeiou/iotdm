package com.aibaixun.iotdm.transport;

/**
 * 传输事务监听者
 * payload 和上报时候一样 如果是json 直接是json 字符串 如果是 二进制 则转换成16进制字符串
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface TransportSessionListener {

    /**
     * 设备配置下发请求
     * @param payload 配置数据
     */
    void on2DeviceConfigReq(String payload);

    /**
     * 设备配置下发请求
     * @param payload ota 数据
     */
    void on2DeviceOtaReq(String payload);

    /**
     * 设备配置下发请求
     * @param payload 命令数据
     */
    void on2DeviceControlReq(String payload);
}
