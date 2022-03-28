package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HttpTargetConfig.class, name = "HTTP"),
        @JsonSubTypes.Type(value = KafkaTargetConfig.class, name = "KAFKA"),
        @JsonSubTypes.Type(value = RabbitTargetConfig.class, name = "RABBIT"),
        @JsonSubTypes.Type(value = MySqlTargetConfig.class, name = "MYSQL")
})
public interface BaseTargetConfig extends Serializable {

    /**
     * 获取资源类型
     * @return 资源类型
     */
    ResourceType getResourceType();
}
