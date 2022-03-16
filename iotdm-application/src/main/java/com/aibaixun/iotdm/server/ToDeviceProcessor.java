package com.aibaixun.iotdm.server;

import com.aibaixun.basic.exception.BaseException;

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
     * @throws BaseException
     */
    void  processConfig(String deviceId,String productId,String host,Integer port) throws BaseException;

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
     * @throws BaseException
     */
    void processControl(String deviceId, String productId,String commandId, Map<String,Object> params) throws BaseException;

    /**
     * 关闭设备连接
     * @param deviceId deviceId
     * @param productId 产品id
     */
    void processCloseConnectDevice(String deviceId,String productId);
}
