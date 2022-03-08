package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.msg.DeviceInfo;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * 设备信息 服务类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface DeviceInfoService {


    /**
     * mqtt 密钥验证
     * @param clientId 客户端id
     * @param username 用户名
     * @param password 密码
     * @return 设备信息
     */
    ListenableFuture<DeviceInfo> mqttDeviceAuthBySecret(String clientId, String username, String password);
}

