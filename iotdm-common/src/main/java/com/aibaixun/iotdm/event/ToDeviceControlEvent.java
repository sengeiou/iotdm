package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.transport.SessionId;

/**
 * 设备命令事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public class ToDeviceControlEvent extends BaseToDeviceEvent {

    private Integer sendId;

    public ToDeviceControlEvent(SessionId sessionId, String payload) {
        super(sessionId, payload);
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }
}
