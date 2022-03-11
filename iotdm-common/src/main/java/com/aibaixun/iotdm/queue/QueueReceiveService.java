package com.aibaixun.iotdm.queue;

import org.springframework.messaging.support.GenericMessage;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface QueueReceiveService {

    /**
     * 接受 消息队列数据
     * @param tsData 数据
     */
    <T> void receiveTsData(GenericMessage<T> tsData);
}
