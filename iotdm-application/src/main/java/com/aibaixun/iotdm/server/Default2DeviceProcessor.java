package com.aibaixun.iotdm.server;


import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 发送到设备 的消息处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@Service
public class Default2DeviceProcessor implements ToDeviceProcessor{

    @Override
    public void processConfig(String deviceId, String productId, String host, Integer port) {

    }

    @Override
    public void processOta(String deviceId, String productId, String otaId) {

    }

    @Override
    public void processControl(String deviceId, String productId, String commandId, Map<String, Object> params) {

    }
}
