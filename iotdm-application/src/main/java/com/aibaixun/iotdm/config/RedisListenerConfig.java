package com.aibaixun.iotdm.config;


import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis 监听配置类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Configuration
public class RedisListenerConfig {

    @Value("${spring.redis.sub.cluster}")
    private String redisSubUri;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClusterClient redisClusterClient(ClientResources clientResources) {
        RedisURI redisUri = RedisURI.create(redisSubUri);
        return RedisClusterClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisClusterConnection<String,String> statefulRedisClusterConnection(RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect();
    }

}
