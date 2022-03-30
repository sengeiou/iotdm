package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发送服务类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public interface SendServer {


     Map<ResourceType, SendServer> SEND_SERVICE_MAP = new ConcurrentHashMap<>(8);


     ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * 发送方法 需要子类实现
     * @param message 消息
     * @param resourceConfig 资源配置
     * @param targetConfig 发送目标配置
     * @param <T> 消息类型
     * @throws  JsonProcessingException
     */
     <T>void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) throws JsonProcessingException;

    /**
     * 需要子类实现 并调用registerService 方法
     */
    void init();


    /**
     * 注册服务
     * @param resourceType 资源类型
     * @param sendService 服务
     */
    default void registerService(ResourceType resourceType, SendServer sendService){
        SEND_SERVICE_MAP.put(resourceType,sendService);
    }

}
