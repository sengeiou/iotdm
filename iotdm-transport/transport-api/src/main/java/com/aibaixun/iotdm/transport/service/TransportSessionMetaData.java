package com.aibaixun.iotdm.transport.service;


import com.aibaixun.iotdm.transport.TransportSessionListener;

/**
 * session 元数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class TransportSessionMetaData {

    private String  deviceId;

    private TransportSessionListener listener;


    public TransportSessionMetaData(String deviceId, TransportSessionListener listener) {
        this.deviceId = deviceId;
        this.listener = listener;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public TransportSessionListener getListener() {
        return listener;
    }

    public void setListener(TransportSessionListener listener) {
        this.listener = listener;
    }
}
