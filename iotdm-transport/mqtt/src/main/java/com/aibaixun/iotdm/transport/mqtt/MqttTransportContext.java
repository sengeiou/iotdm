package com.aibaixun.iotdm.transport.mqtt;

import com.aibaixun.iotdm.transport.TransportContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mqtt 传输上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Component
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportContext extends TransportContext {



    @Value("${transport.mqtt.netty.max_payload_size}")
    private Integer maxPayloadSize;

    /**
     * ssl handler provider
     */
    private MqttSslHandlerProvider sslHandlerProvider;

    /**
     * mqtt 连接数
     */
    private final AtomicInteger connectionsCounter = new AtomicInteger();


    public boolean checkAddress (InetSocketAddress inetSocketAddress) {
        return transportRateLimitService.checkAddress(inetSocketAddress);
    }


    public Integer getMaxPayloadSize() {
        return maxPayloadSize;
    }

    @Autowired
    public void setSslHandlerProvider(MqttSslHandlerProvider sslHandlerProvider) {
        this.sslHandlerProvider = sslHandlerProvider;
    }

    public MqttSslHandlerProvider getSslHandlerProvider() {
        return sslHandlerProvider;
    }


    /**
     * 连接数 +1
     */
    public void channelRegistered() {
        connectionsCounter.incrementAndGet();
    }

    /**
     * 连接数 -1
     */
    public void channelUnregistered() {
        connectionsCounter.decrementAndGet();
    }


    /**
     * 设备认证成功
     * @param address 地址信息
     */
    public void onDeviceAuthSuccess(InetSocketAddress address) {
        transportRateLimitService.onDeviceAuthSuccess(address);
    }

    /**
     * 设备认证 失败
     * @param address 地址信息
     */
    public void onDeviceAuthFailure(InetSocketAddress address) {
        transportRateLimitService.onDeviceAuthFailure(address);
    }


}
