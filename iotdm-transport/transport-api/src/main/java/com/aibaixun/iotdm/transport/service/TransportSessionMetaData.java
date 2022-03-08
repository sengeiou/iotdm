package com.aibaixun.iotdm.transport.service;

import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.transport.TransportSessionListener;

/**
 * session 元数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class TransportSessionMetaData {

    private TransportSessionInfo transportSessionInfo;

    private TransportSessionListener listener;


    public TransportSessionMetaData(TransportSessionInfo transportSessionInfo, TransportSessionListener listener) {
        this.transportSessionInfo = transportSessionInfo;
        this.listener = listener;
    }

    public TransportSessionInfo getTransportSessionInfo() {
        return transportSessionInfo;
    }

    public void setTransportSessionInfo(TransportSessionInfo transportSessionInfo) {
        this.transportSessionInfo = transportSessionInfo;
    }

    public TransportSessionListener getListener() {
        return listener;
    }

    public void setListener(TransportSessionListener listener) {
        this.listener = listener;
    }
}
