package com.aibaixun.iotdm.transport.mqtt;

import com.aibaixun.iotdm.transport.mqtt.limits.BaseRemoteAddressFilter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

/**
 * Mqtt 服务初始化类类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class MqttTransportServerInitializer extends ChannelInitializer<SocketChannel> {

    private final MqttTransportContext context;

    private final boolean sslEnabled;

    public MqttTransportServerInitializer(MqttTransportContext context, boolean sslEnabled) {
        this.context = context;
        this.sslEnabled = sslEnabled;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (isSslEnabled()){
            pipeline.addLast(context.getSslHandlerProvider().getSslHandler());
        };
        pipeline.addLast("BaseRemoteAddressFilter", new BaseRemoteAddressFilter(context));
        pipeline.addLast("decoder", new MqttDecoder(context.getMaxPayloadSize()));
        pipeline.addLast("encoder", MqttEncoder.INSTANCE);
        MqttTransportHandler handler = new MqttTransportHandler(context);
        pipeline.addLast(handler);
        ch.closeFuture().addListener(handler);
    }


    public boolean isSslEnabled() {
        return sslEnabled;
    }
}
