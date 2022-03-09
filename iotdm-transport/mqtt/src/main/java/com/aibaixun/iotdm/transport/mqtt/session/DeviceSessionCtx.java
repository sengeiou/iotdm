package com.aibaixun.iotdm.transport.mqtt.session;

import com.aibaixun.iotdm.transport.mqtt.MqttTransportContext;
import com.aibaixun.iotdm.transport.session.DeviceAwareSessionContext;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.aibaixun.iotdm.constants.DataConstants.IOT_SESSION_CACHE_KEY_PREFIX;

/**
 * 设备 连接 session 信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class DeviceSessionCtx extends DeviceAwareSessionContext {


    private ChannelHandlerContext channel;


    private final MqttTransportContext context;


    private final AtomicInteger msgIdSeq = new AtomicInteger(0);


    private volatile String deviceId;


    private volatile String productId;


    public DeviceSessionCtx(UUID sessionId, MqttTransportContext context) {
        super(sessionId);
        this.context = context;
    }

    @Override
    public int nextMsgId() {
        return msgIdSeq.incrementAndGet();
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getProductId() {
        return productId;
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


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
