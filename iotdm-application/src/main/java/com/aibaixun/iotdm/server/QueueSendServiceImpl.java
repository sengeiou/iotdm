package com.aibaixun.iotdm.server;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.business.MessageBusinessMsg;
import com.aibaixun.iotdm.business.PostPropertyBusinessMsg;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.queue.IotDmSource;
import com.aibaixun.iotdm.queue.QueueSendService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Service
@EnableBinding(value = IotDmSource.class)
public class QueueSendServiceImpl implements QueueSendService {

    private final IotDmSource iotDmSource;


    @Override
    public void sendSessionData(DeviceSessionEvent deviceSessionEvent) {
        iotDmSource.outputSessionData().send(MessageBuilder.withPayload(JsonUtil.toJSONString(deviceSessionEvent)).build());
    }

    @Override
    public void sendPropertyTsData(PostPropertyBusinessMsg postPropertyBusinessMsg) {
        iotDmSource.outputPropertyData().send(MessageBuilder.withPayload(JsonUtil.toJSONString(postPropertyBusinessMsg)).build());
    }


    @Override
    public void sendMessageTsData(MessageBusinessMsg messageBusinessMsg) {
        iotDmSource.outputMessageData().send(MessageBuilder.withPayload(JsonUtil.toJSONString(messageBusinessMsg)).build());
    }


    @Override
    public void sendEntityChangeData(EntityChangeEvent entityChangeEvent) {

    }

    public QueueSendServiceImpl(IotDmSource iotDmSource) {
        this.iotDmSource = iotDmSource;
    }
}
