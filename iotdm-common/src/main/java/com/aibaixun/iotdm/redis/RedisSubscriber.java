package com.aibaixun.iotdm.redis;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.cluster.pubsub.api.async.NodeSelectionPubSubAsyncCommands;
import io.lettuce.core.cluster.pubsub.api.async.PubSubAsyncNodeSelection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
@ConditionalOnExpression("!'${spring.redis.sub.cluster:}'.isEmpty()")
public class RedisSubscriber extends RedisPubSubAdapter<String,String> implements ApplicationRunner {


    private RedisClusterListener redisClusterListener;


    private RedisClusterClient redisClusterClient;

    public void startListener(){
        StatefulRedisClusterPubSubConnection<String, String> pubSubConnection = redisClusterClient.connectPubSub();
        pubSubConnection.setNodeMessagePropagation(true);
        pubSubConnection.addListener(redisClusterListener);
        PubSubAsyncNodeSelection<String, String> masters = pubSubConnection.async().masters();
        NodeSelectionPubSubAsyncCommands<String, String> commands = masters.commands();
        commands.subscribe(EXPIRED_CHANNEL);
    }

    @Override
    public void run(ApplicationArguments args) {
        startListener();
    }


    @Autowired
    public void setRedisClusterListener(RedisClusterListener redisClusterListener) {
        this.redisClusterListener = redisClusterListener;
    }

    @Autowired
    public void setRedisClusterClient(RedisClusterClient redisClusterClient) {
        this.redisClusterClient = redisClusterClient;
    }
}
