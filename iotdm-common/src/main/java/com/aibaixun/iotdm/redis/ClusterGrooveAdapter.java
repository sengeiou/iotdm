package com.aibaixun.iotdm.redis;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.cluster.pubsub.api.async.NodeSelectionPubSubAsyncCommands;
import io.lettuce.core.cluster.pubsub.api.async.PubSubAsyncNodeSelection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.aibaixun.iotdm.constants.DataConstants.EXPIRED_CHANNEL;

/**
 * redis 订阅
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
public class ClusterGrooveAdapter extends RedisPubSubAdapter {
 

 
    @Resource
    RedisClusterClient clusterClient;

    @Autowired
    private RedisClusterListener clusterGrooveAdapter;

 
    /**
     * 启动监听
     */
    @PostConstruct
    public void startListener() {
        StatefulRedisClusterPubSubConnection<String, String> pubSubConnection = clusterClient.connectPubSub();
        pubSubConnection.setNodeMessagePropagation(true);
        pubSubConnection.addListener(clusterGrooveAdapter);
        PubSubAsyncNodeSelection<String, String> masters = pubSubConnection.async().masters();
        NodeSelectionPubSubAsyncCommands<String, String> commands = masters.commands();
        commands.subscribe(EXPIRED_CHANNEL);
    }
}