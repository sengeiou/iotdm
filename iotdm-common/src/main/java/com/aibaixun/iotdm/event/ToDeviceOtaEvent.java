package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.transport.SessionId;

/**
 * ota  事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public class ToDeviceOtaEvent extends BaseToDeviceEvent {

    public ToDeviceOtaEvent(SessionId sessionId, String payload) {
        super(sessionId, payload);
    }
}
