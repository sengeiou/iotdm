package com.aibaixun.iotdm.transport.mqtt.limits;

import com.aibaixun.iotdm.transport.mqtt.MqttTransportContext;
import com.aibaixun.iotdm.transport.mqtt.MqttTransportService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ipfilter.AbstractRemoteAddressFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 远程 地址过滤
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class BaseRemoteAddressFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {


    private final Logger logger  = LoggerFactory.getLogger("mqtt-transport");


    private final MqttTransportContext context;


    public BaseRemoteAddressFilter(MqttTransportContext context) {
        this.context = context;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress remoteAddress) {
        logger.trace("[{}] Received msg: {}", channelHandlerContext.channel().id(), remoteAddress);
        if(context.checkAddress(remoteAddress)){
            logger.trace("[{}] Setting address: {}", channelHandlerContext.channel().id(), remoteAddress);
            channelHandlerContext.channel().attr(MqttTransportService.ADDRESS).set(remoteAddress);
            return true;
        } else {
            return false;
        }
    }
}
