package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.rule.pool.PoolResource;
import com.aibaixun.iotdm.rule.pool.ResourceLruCache;
import com.aibaixun.iotdm.support.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.cache.LRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Properties;

/**
 * @author huanghaijiang
 * kafka客户端服务
 */
@Component
public class KafkaSendService implements SendService {
    private final Logger log = LoggerFactory.getLogger(HttpSendService.class);



    @Value("${bx.rule.kafka.max-idle-connections}")
    private Integer maxIdleConnections;

    private ResourceLruCache<String, KafkaConnectionResource> kafkaConnections;

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
            KafkaProducer<String,String> kafkaProducer = generateClient(kafkaResourceConfig);
            KafkaTargetConfig kafkaTargetConfig = (KafkaTargetConfig) targetConfig;

            kafkaProducer.send(new ProducerRecord<>(kafkaTargetConfig.getTopic(), OBJECT_MAPPER.writeValueAsString(message)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        log.error("KafkaSendService.doSendMessage >> send result is error ,error msg is :{}", e.getMessage());
                    } else {
                        log.info("KafkaSendService.doSendMessage >> is success , recordMetadata is :{}", recordMetadata);
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
    @PostConstruct
    public void init() {
        registerService(ResourceType.KAFKA,this);
        kafkaConnections = new ResourceLruCache<>(maxIdleConnections);
    }

    static class KafkaConnectionResource implements PoolResource {
        private final KafkaProducer<String,String>  kafkaProducer;

        @Override
        public void releaseResource() {
            if (kafkaProducer != null){
                kafkaProducer.close();
            }
        }
        public KafkaConnectionResource(KafkaProducer<String, String> kafkaProducer) {
            this.kafkaProducer = kafkaProducer;
        }
    }


    public  KafkaProducer<String,String> generateClient(KafkaResourceConfig config) {

        String host = config.getHost();
        KafkaConnectionResource kafkaConnectionResource = kafkaConnections.get(host);
        if (Objects.isNull(kafkaConnectionResource)){
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHost());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            Integer connectTime = Objects.nonNull(config.getConnectTimeout())?config.getConnectTimeout()*1000:6000;
            properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, connectTime);
            String compressionType = Objects.nonNull(config.getCompressionType())?config.getCompressionType():"gzip";
            properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
            Integer reqTimeout  = Objects.nonNull(config.getReqTimeout())?config.getReqTimeout()*1000:6000;
            properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, reqTimeout);
            Integer metaUpdateTime  = Objects.nonNull(config.getMetadataUpdateTime())?config.getMetadataUpdateTime()*100:6000;
            properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, metaUpdateTime);
            if (Objects.nonNull(config.getBufferSize())){
                properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.getBufferSize());
            }
            if (Objects.nonNull(config.getBatchSize())){
                properties.put(ProducerConfig.BATCH_SIZE_CONFIG, config.getBatchSize());
            }
            if (Objects.nonNull(config.getUsername())){
                properties.put("username", config.getUsername());
            }
            if (Objects.nonNull(config.getPassword())){
                properties.put("password", config.getPassword());
            }
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            kafkaConnectionResource = new KafkaConnectionResource(producer);
            kafkaConnections.put(host,kafkaConnectionResource);
        }
        return kafkaConnectionResource.kafkaProducer;
    }
}
