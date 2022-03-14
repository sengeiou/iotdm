package com.aibaixun.iotdm.queue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface IotDmSink {


    String INPUT_PROPERTY_TS_DATA = "input_property_data";
    String INPUT_MESSAGE_TS_DATA = "input_message_data";
    String INPUT_SESSION_DATA = "input_session_data";
    String INPUT_ENTITY_DATA = "input_entity_data";


    /**
     * ts 时序数据 输入通道
     * @return 通道
     */
    @Input(INPUT_PROPERTY_TS_DATA)
    SubscribableChannel inputPropertyTsData();



    /**
     * ts 时序数据 输入通道
     * @return 通道
     */
    @Input(INPUT_MESSAGE_TS_DATA)
    SubscribableChannel inputMessageTsData();

    /**
     * session 数据输入通道
     * @return 通道
     */
    @Input(INPUT_SESSION_DATA)
    SubscribableChannel inputSessionData();



    /**
     * 实体更改 数据输入通道
     * @return 通道
     */
    @Input(INPUT_ENTITY_DATA)
    SubscribableChannel inputEntityData();
}
