package com.aibaixun.iotdm.server;

import java.util.Map;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public interface ToDeviceProcessor {

    /**
     * 修改设备配置
     * @param deviceId 设备id
     * @param host 连接地址
     * @param productId 产品id
     * @param port 连接端口
     */
    void  processConfig(String deviceId,String productId,String host,Integer port);

    /**
     * 处理ota 消息
     * @param deviceId 设备id
     * @param productId 产品id
     * @param otaId ota包id
     */
    void processOta(String deviceId,String productId,String otaId);

    /**
     * 命令下发
     * @param deviceId 设备id
     * @param commandId 命令id
     * @param productId 产品id
     * @param params 参数
     */
    void processControl(String deviceId, String productId,String commandId, Map<String,Object> params);
}
