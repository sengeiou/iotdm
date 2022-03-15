package com.aibaixun.iotdm.transport.limits;

import java.net.InetSocketAddress;

/**
 * 传输 限制类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface TransportLimitServer {


    /**
     * 检查地址 是否可以连接
     * @param address 地址
     * @return 是否能连接
     */
    boolean checkAddress(InetSocketAddress address);


    /**
     * 设备认证成功
     * @param address 地址信息
     */
    void onDeviceAuthSuccess(InetSocketAddress address);

    /**
     * 设备 认证失败
     * @param address 地址信息
     */
    void onDeviceAuthFailure(InetSocketAddress address);


    /**
     * 租户连接数限制
     * @param tenantId 租户id
     * @return 是否到达定点
     */
    boolean checkTenantLimit(String tenantId);

}
