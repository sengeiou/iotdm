package com.aibaixun.iotdm.event;

/**
 * 设备更新配置反馈
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public class DeviceConfigRespEvent extends BaseToPlatformEvent {



    public DeviceConfigRespEvent() {
    }

    public DeviceConfigRespEvent(String deviceId, String productId) {
        super(deviceId, productId);
    }


}
