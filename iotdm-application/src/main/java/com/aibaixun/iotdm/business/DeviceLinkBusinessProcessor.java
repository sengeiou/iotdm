package com.aibaixun.iotdm.business;

import org.springframework.stereotype.Component;

/**
 * 设备联动
 * @author wangxiao@aibaixun.com
 * @date 2022/3/28
 */

public class DeviceLinkBusinessProcessor  extends AbstractReportProcessor<PostPropertyBusinessMsg,MessageBusinessMsg>{

    @Override
    public void processProperty(PostPropertyBusinessMsg propertyBusinessMsg) {
        //
    }

    @Override
    public void processMessage(MessageBusinessMsg messageBusinessMsg) {
        // nothing to do
    }
}
