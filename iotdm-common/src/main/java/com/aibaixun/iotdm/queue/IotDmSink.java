package com.aibaixun.iotdm.queue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public interface IotDmSink {


    String INPUT_TS_DATA = "input_property_data";


    /**
     * ts 时序数据 输入通道
     * @return 通道
     */
    @Input(INPUT_TS_DATA)
    SubscribableChannel inputTsData();
}
