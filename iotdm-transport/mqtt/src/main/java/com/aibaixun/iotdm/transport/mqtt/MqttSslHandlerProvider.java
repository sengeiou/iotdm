package com.aibaixun.iotdm.transport.mqtt;

import com.aibaixun.iotdm.transport.TransportService;
import io.netty.handler.ssl.SslHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${transport.mqtt.ssl.protocol}")
    private String sslProtocol;


    private TransportService transportService;


    @Autowired
    public void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }


    public SslHandler getSslHandler() {
        return new SslHandler(null);
    }
}
