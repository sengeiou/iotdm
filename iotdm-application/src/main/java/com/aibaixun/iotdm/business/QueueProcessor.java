package com.aibaixun.iotdm.business;

import org.springframework.stereotype.Component;

/**
 * 消息队列处理器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class QueueProcessor extends AbstractReportProcessor<PostPropertyBusinessMsg,MessageBusinessMsg>{

    @Override
    public void processProperty(PostPropertyBusinessMsg propertyBusinessMsg) {

    }

    @Override
    public void processMessage(MessageBusinessMsg messageBusinessMsg) {

    }
}
