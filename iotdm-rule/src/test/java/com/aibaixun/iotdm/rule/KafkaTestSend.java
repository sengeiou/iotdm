package com.aibaixun.iotdm.rule;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.rule.pool.PoolResource;
import com.aibaixun.iotdm.support.KafkaResourceConfig;
import com.aibaixun.iotdm.support.KafkaTargetConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.cache.LRUCache;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;

import static com.aibaixun.iotdm.rule.send.SendService.OBJECT_MAPPER;


/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/22
 */
public class KafkaTestSend {

    private static LRUCache<String, KafkaConnectionResource> kafkaConnections;

    public static void main(String[] args) throws JsonProcessingException {
        kafkaConnections =  new LRUCache<>(10);
        String kafkaConfig = "{\"host\": \"172.16.7.136:9092\", \"password\": null, \"username\": null, \"batchSize\": null, \"bufferSize\": null, \"reqTimeout\": null, \"resourceType\": \"KAFKA\", \"connectTimeout\": null, \"compressionType\": \"gzip\", \"metadataUpdateTime\": null}";
        KafkaResourceConfig kafkaResourceConfig = JsonUtil.toObject(kafkaConfig, KafkaResourceConfig.class);
        String kafkaTargetConfig = "{\"topic\": \"mykafka\", \"messageKey\": \"none\", \"produceType\": \"random\", \"resourceType\": \"KAFKA\", \"produceStrategy\": \"async\"}";
        KafkaTargetConfig targetConfig = JsonUtil.toObject(kafkaTargetConfig, KafkaTargetConfig.class);
        KafkaProducer<String,String> kafkaProducer = generateClient(kafkaResourceConfig);
        String message = "hello";
        kafkaProducer.send(new ProducerRecord<>(targetConfig.getTopic(), OBJECT_MAPPER.writeValueAsString(message)), (recordMetadata, e) -> {
            if (e== null){
                System.out.println("success");
            }else {
                System.out.println(e.getMessage());
            }
        });
    }


    public static KafkaProducer<String,String> generateClient(KafkaResourceConfig config) {

        String host = config.getHost();
        KafkaConnectionResource kafkaConnectionResource = kafkaConnections.get(host);
        if (Objects.isNull(kafkaConnectionResource)) {
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHost());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            Integer connectTime = Objects.nonNull(config.getConnectTimeout()) ? config.getConnectTimeout() : 6000;
            properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, connectTime);
            String compressionType = Objects.nonNull(config.getCompressionType()) ? config.getCompressionType() : "gzip";
            properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
            Integer reqTimeout = Objects.nonNull(config.getReqTimeout()) ? config.getReqTimeout() : 6000;
            properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, reqTimeout);
            Integer metaUpdateTime = Objects.nonNull(config.getMetadataUpdateTime()) ? config.getMetadataUpdateTime() : 6000;
            properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, metaUpdateTime);
            if (Objects.nonNull(config.getBufferSize())) {
                properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.getBufferSize());
            }
            if (Objects.nonNull(config.getBatchSize())) {
                properties.put(ProducerConfig.BATCH_SIZE_CONFIG, config.getBatchSize());
            }
            if (Objects.nonNull(config.getUsername())) {
                properties.put("username", config.getUsername());
            }
            if (Objects.nonNull(config.getPassword())) {
                properties.put("password", config.getPassword());
            }
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            kafkaConnectionResource = new KafkaConnectionResource(producer);
            kafkaConnections.put(host, kafkaConnectionResource);
        }
        return kafkaConnectionResource.kafkaProducer;
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
}
