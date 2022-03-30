package com.aibaixun.iotdm.rule;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.rule.pool.PoolResource;
import com.aibaixun.iotdm.rule.pool.ResourceLruCache;
import com.aibaixun.iotdm.support.RabbitResourceConfig;
import com.aibaixun.iotdm.support.RabbitTargetConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.aibaixun.iotdm.rule.send.SendServer.OBJECT_MAPPER;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/22
 */
public class RabbitTestSend {

    private static ResourceLruCache<String, RabbitConnectionResource> connectionPool;

    public static void main(String[] args) {
        connectionPool = new ResourceLruCache<>(10);
        String resource = "{\"host\": \"172.16.9.10\", \"port\":5672, \"keepLive\": 60, \"password\": \"HzCbwgjY8c51WgYD\", \"username\": \"iotdm\", \"virtualHost\": \"/\", \"resourceType\": \"RABBIT\", \"connectTimeout\": 6000, \"connectPoolSize\": 1000}";
        RabbitResourceConfig rabbitResourceConfig = JsonUtil.toObject(resource, RabbitResourceConfig.class);
        RabbitTargetConfig rabbitTargetConfig = new RabbitTargetConfig();
        rabbitTargetConfig.setExchange("/test");
        rabbitTargetConfig.setRoutingKey("test");
        rabbitTargetConfig.setExchangeType(BuiltinExchangeType.TOPIC);
        String host = rabbitResourceConfig.getHost();
        int port = rabbitResourceConfig.getPort();
        String virtualHost = rabbitResourceConfig.getVirtualHost();
        String username = rabbitResourceConfig.getUsername();
        String password = rabbitResourceConfig.getPassword();
        Integer connectTimeout = rabbitResourceConfig.getConnectTimeout();
        Integer keepLive = rabbitResourceConfig.getKeepLive();
        RabbitConnectionResource rabbitConnection = connectionPool.get(host+port);
        if (Objects.isNull(rabbitConnection)) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setVirtualHost(virtualHost);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setAutomaticRecoveryEnabled(true);
            try {
                Connection connection = factory.newConnection();
                rabbitConnection = new RabbitConnectionResource(connection);
                String exchangeName = rabbitTargetConfig.getExchange();
                String routingKey = rabbitTargetConfig.getRoutingKey();
                BuiltinExchangeType exchangeType = rabbitTargetConfig.getExchangeType();
                if (Objects.isNull(rabbitConnection.channel)) {
                    return;
                }
                Channel channel = rabbitConnection.channel;
                String data = OBJECT_MAPPER.writeValueAsString("hello");
                channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
                channel.basicPublish(
                        exchangeName,
                        routingKey,
                        MessageProperties.BASIC,
                        data.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("RabbitSendService.doSendMessage >> create connection is error, msg is:{}"+e.getMessage());
            }
        }


    }

    static class RabbitConnectionResource implements PoolResource {
        private final Connection connection;

        private final Channel channel;
        @Override
        public void releaseResource() {
            try {
                if (connection!= null){
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            };
        }

        public RabbitConnectionResource(Connection connection) throws IOException {
            this.connection = connection;
            this.channel = connection.createChannel();
        }
    }
}
