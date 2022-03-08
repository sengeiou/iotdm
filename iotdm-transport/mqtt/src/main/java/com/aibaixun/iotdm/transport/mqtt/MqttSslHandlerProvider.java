package com.aibaixun.iotdm.transport.mqtt;

import io.netty.handler.ssl.SslHandler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * Mqtt transport ssl provider
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Component("MqttSslHandlerProvider")
@ConditionalOnProperty(prefix = "transport.mqtt.ssl", value = "enabled", havingValue = "true")
public class MqttSslHandlerProvider {


    public SslHandler getSslHandler() {
        return null;
    }


}
