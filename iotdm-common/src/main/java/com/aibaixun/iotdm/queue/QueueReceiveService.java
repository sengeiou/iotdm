package com.aibaixun.iotdm.queue;

import org.springframework.messaging.support.GenericMessage;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface QueueReceiveService {

    /**
     * 接受 消息队列的时序数据
     * @param tsData 数据
     */
    <T> void receivePropertyTsData(GenericMessage<T> tsData);


    /**
     * 接受 消息队列的时序数据
     * @param tsData 数据
     */
    <T> void receiveMessageTsData(GenericMessage<T> tsData);



    /**
     * 接受 消息队列session数据
     * @param sessionData 数据
     */
    <T> void receiveSessionData(GenericMessage<T> sessionData);



    /**
     * 接受 实体数据数据
     * @param entityData 数据
     */
    <T> void receiveEntityData(GenericMessage<T> entityData);
}
