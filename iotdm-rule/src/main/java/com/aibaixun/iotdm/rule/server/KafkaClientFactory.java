package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.rule.config.KafkaClientConfig;
import com.aibaixun.iotdm.support.KafkaResourceConfig;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class KafkaClientFactory {
    static ConcurrentMap<String, KafkaProducer> products = new ConcurrentHashMap<>();

    public static KafkaProducer generateClient(KafkaResourceConfig config, KafkaClientConfig kafkaClientConfig) {
        if (products.containsKey(config.getHost())) {
            return products.get(config.getHost());
        } else {
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHost());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, kafkaClientConfig.getConnectTimeout());
            properties.put("username", config.getUsername());
            properties.put("password", config.getPassword());
            KafkaProducer<?, String> producer = new KafkaProducer<>(properties);
            products.put(config.getHost(), producer);
            return producer;
        }
    }
}
