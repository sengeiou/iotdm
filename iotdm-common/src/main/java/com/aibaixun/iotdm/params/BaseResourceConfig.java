package com.aibaixun.iotdm.params;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 资源 配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType",
        visible = true)
public interface BaseResourceConfig {

    /**
     * 获取资源类型
     * @return 资源类型
     */
    ResourceType getResourceType();
}
