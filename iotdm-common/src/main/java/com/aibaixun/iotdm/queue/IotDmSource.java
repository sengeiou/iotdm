package com.aibaixun.iotdm.queue;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface IotDmSource {

    String OUTPUT_SESSION_DATA = "out_session_data";

    String OUTPUT_PROPERTY_TS_DATA = "out_property_ts_data";
    String OUTPUT_MESSAGE_TS_DATA = "out_message_ts_data";

    /**
     * session 信息输出通道
     * @return 通道
     */
    @Output(OUTPUT_SESSION_DATA)
    MessageChannel outputSessionData();



    /**
     * 时序数据通道
     * @return 通道
     */
    @Output(OUTPUT_PROPERTY_TS_DATA)
    MessageChannel outputPropertyData();


    /**
     * 时序数据通道
     * @return 通道
     */
    @Output(OUTPUT_MESSAGE_TS_DATA)
    MessageChannel outputMessageData();
}
