package com.aibaixun.iotdm.redis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LettuceConfig {
 
    @Value("${spring.redis.sub.cluster}")
    String redisSubURI;
 
    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }
 
    @Bean(destroyMethod = "shutdown")
    RedisClusterClient redisClusterClient(ClientResources clientResources) {
 
        RedisURI redisURI = RedisURI.create(redisSubURI);
 
        return RedisClusterClient.create(clientResources, redisURI);
    }
 
    @Bean(destroyMethod = "close")
    StatefulRedisClusterConnection statefulRedisClusterConnection(RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect();
    }
}