package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实体更改处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
public class EntityProcessor {

    private QueueSendServer queueSendService;

    public void  doProcessEntityChangeEvent (EntityChangeEvent entityChangeEvent){
        queueSendService.sendEntityChangeData(entityChangeEvent);
    }

    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }
}
