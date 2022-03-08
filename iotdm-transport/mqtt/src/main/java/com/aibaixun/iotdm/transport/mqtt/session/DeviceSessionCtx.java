package com.aibaixun.iotdm.transport.mqtt.session;

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

    public DeviceSessionCtx(UUID sessionId, MqttTransportContext context) {
        super(sessionId);
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
}
