package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.queue.QueueSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列处理器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class QueueProcessor extends AbstractReportProcessor<PostPropertyBusinessMsg,MessageBusinessMsg>{

    private QueueSendService queueSendService;

    @Override
    public void processProperty(PostPropertyBusinessMsg propertyBusinessMsg) {
        queueSendService.sendPropertyTsData(propertyBusinessMsg);
    }

    @Override
    public void processMessage(MessageBusinessMsg messageBusinessMsg) {
        queueSendService.sendMessageTsData(messageBusinessMsg);
    }

    @Autowired
    public void setQueueSendService(QueueSendService queueSendService) {
        this.queueSendService = queueSendService;
    }
}
