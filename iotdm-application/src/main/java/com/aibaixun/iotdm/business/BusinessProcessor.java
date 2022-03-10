package com.aibaixun.iotdm.business;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface BusinessProcessor {

    void doProcessProperty(PropertyBusinessMsg propertyBusinessMsg);

    void doProcessMessage (JsonNode jsonNode);
}
