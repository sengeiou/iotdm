package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.rule.config.KafkaClientConfig;
import com.aibaixun.iotdm.rule.server.KafkaClientFactory;
import com.aibaixun.iotdm.support.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * kafka客户端服务
 */
@Component
public class KafkaSendService implements SendService {
    private final Logger log = LoggerFactory.getLogger(HttpSendService.class);
    @Autowired
    private KafkaClientConfig kafkaClientConfig;

    /**
     * 发送方法 需要子类实现
     *
     * @param message        消息
     * @param resourceConfig 资源配置
     * @param targetConfig   发送目标配置
     */
    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) {
        try {
            KafkaResourceConfig kafkaResourceConfig = (KafkaResourceConfig) resourceConfig;
            KafkaProducer kafkaProducer = KafkaClientFactory.generateClient(kafkaResourceConfig, kafkaClientConfig);
            KafkaTargetConfig kafkaTargetConfig = (KafkaTargetConfig) targetConfig;
            kafkaProducer.send(new ProducerRecord<>(kafkaTargetConfig.getTopic(), OBJECT_MAPPER.writeValueAsString(message)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (recordMetadata == null) {
                        log.error("KafkaSendService.doSendMessage >> is error ,error msg is :{}", e.getMessage());
                    } else {
                        log.info("KafkaSendService.doSendMessage >> is error ,error msg is :{}", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            log.error("KafkaSendService.doSendMessage >> is error ,error msg is :{}", e.getMessage());
        }
    }

    /**
     * 需要子类实现 并调用registerService 方法
     */
    @Override
    public void init() {

    }

    /**
     * 注册服务
     *
     * @param resourceType 资源类型
     * @param sendService  服务
     */
    @Override
    public void registerService(ResourceType resourceType, SendService sendService) {
        SendService.super.registerService(resourceType, sendService);
    }
}
