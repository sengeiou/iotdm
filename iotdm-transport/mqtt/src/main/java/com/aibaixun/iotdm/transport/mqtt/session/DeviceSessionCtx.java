package com.aibaixun.iotdm.transport.mqtt.session;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.transport.SessionId;
import com.aibaixun.iotdm.transport.mqtt.MqttTransportContext;
import com.aibaixun.iotdm.transport.session.DeviceAwareSessionContext;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 设备 连接 session 信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DeviceSessionCtx extends DeviceAwareSessionContext {

    private ChannelHandlerContext channel;

    private final MqttTransportContext context;

    private final AtomicInteger msgIdSeq = new AtomicInteger(0);

    private volatile SessionId sessionId;

    private volatile DataFormat dataFormat;

    private volatile boolean subscribeControl;

    private volatile boolean subscribeConfig;


    private volatile boolean subscribeOta;


    public DeviceSessionCtx( MqttTransportContext context) {
        this.context = context;
    }

    @Override
    public int nextMsgId() {
        return msgIdSeq.incrementAndGet();
    }


    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }


    public ChannelHandlerContext getChannel() {
        return channel;
    }


    public MqttTransportContext getContext() {
        return context;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public AtomicInteger getMsgIdSeq() {
        return msgIdSeq;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }


    public boolean isSubscribeControl() {
        return subscribeControl;
    }

    public void setSubscribeControl(boolean subscribeControl) {
        this.subscribeControl = subscribeControl;
    }

    public boolean isSubscribeConfig() {
        return subscribeConfig;
    }

    public void setSubscribeConfig(boolean subscribeConfig) {
        this.subscribeConfig = subscribeConfig;
    }

    public boolean isSubscribeOta() {
        return subscribeOta;
    }

    public void setSubscribeOta(boolean subscribeOta) {
        this.subscribeOta = subscribeOta;
    }

    public SessionId getSessionId() {
        return sessionId;
    }
}
