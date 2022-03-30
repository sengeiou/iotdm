package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.rule.pool.ResourceLruCache;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import com.aibaixun.iotdm.support.RabbitResourceConfig;
import com.aibaixun.iotdm.support.RabbitTargetConfig;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
public class RabbitSendServer implements SendServer {

    private final Logger log = LoggerFactory.getLogger(RabbitSendServer.class);

    private ResourceLruCache<Connection> resourceLruCache;


    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig)  {

        RabbitResourceConfig rabbitResourceConfig = (RabbitResourceConfig) resourceConfig;
        RabbitTargetConfig rabbitTargetConfig = (RabbitTargetConfig) targetConfig;
        try {
            Connection connection = getConnection(rabbitResourceConfig);
            String exchangeName = rabbitTargetConfig.getExchange();
            String routingKey = rabbitTargetConfig.getRoutingKey();
            BuiltinExchangeType exchangeType = rabbitTargetConfig.getExchangeType();
            Channel channel = connection.createChannel();
            String data = OBJECT_MAPPER.writeValueAsString(message);
            channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
            channel.basicPublish(
                    exchangeName,
                    routingKey,
                    MessageProperties.BASIC,
                    data.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
            log.error("RabbitSendService.doSendMessage >> create connection is error, msg is:{}",e.getMessage());
        }
    }



    @Override
    @PostConstruct
    public void init() {
        registerService(ResourceType.RABBIT,this);
        resourceLruCache = new ResourceLruCache<>(2000);
    }


    private Connection getConnection(RabbitResourceConfig resourceConfig)  {
        String host = resourceConfig.getHost();
        int port = resourceConfig.getPort();
        Connection connection = resourceLruCache.get(host + port);
        if (Objects.isNull(connection)){
            String virtualHost = resourceConfig.getVirtualHost();
            String username = resourceConfig.getUsername();
            String password = resourceConfig.getPassword();
            Integer connectTimeout = resourceConfig.getConnectTimeout();
            Integer keepLive = resourceConfig.getKeepLive();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setVirtualHost(virtualHost);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setAutomaticRecoveryEnabled(true);
            if (Objects.nonNull(connectTimeout) && connectTimeout!= 0){
                factory.setConnectionTimeout(connectTimeout*1000);
            }
            if (Objects.nonNull(keepLive) && keepLive != 0){
                factory.setHandshakeTimeout(keepLive*1000);
            }

            try {
                connection= factory.newConnection();
                resourceLruCache.put(host + port,connection);
            }catch (Exception e){
                log.warn("RabbitSendService.getConnection is error,error msg is:{}",e.getMessage());
            }
        }
        return connection;
    }




}
