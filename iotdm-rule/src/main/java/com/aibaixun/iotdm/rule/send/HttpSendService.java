package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public class HttpSendService implements SendService{

    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) {

    }

    @Override
    public void init() {
        registerService(ResourceType.HTTP,this);
    }
}
