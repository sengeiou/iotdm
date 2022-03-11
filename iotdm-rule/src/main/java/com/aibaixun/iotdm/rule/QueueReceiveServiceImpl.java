package com.aibaixun.iotdm.rule;

import com.aibaixun.iotdm.queue.IotDmSink;
import com.aibaixun.iotdm.queue.QueueReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Service
@EnableBinding(value = { IotDmSink.class })
public class QueueReceiveServiceImpl implements QueueReceiveService {

    private final Logger log = LoggerFactory.getLogger(QueueReceiveServiceImpl.class);

    @Override
    @StreamListener(IotDmSink.INPUT_TS_DATA)
    public <T> void receiveTsData(GenericMessage<T> tsData) {
        log.info("revice data:{}",tsData);

    }
}
