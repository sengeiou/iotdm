package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.transport.SessionId;

/**
 * 设备配置事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public class ToDeviceConfigEvent extends BaseToDeviceEvent {


    public ToDeviceConfigEvent(SessionId sessionId, String payload) {
        super(sessionId, payload);
    }
}
