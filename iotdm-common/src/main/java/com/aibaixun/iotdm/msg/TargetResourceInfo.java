package com.aibaixun.iotdm.msg;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;

/**
 * 转发目标资源信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
public class TargetResourceInfo {

    public String id;

    public ResourceType resourceType;

    private BaseResourceConfig resourceConfig;

    private BaseTargetConfig targetConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public BaseResourceConfig getResourceConfig() {
        return resourceConfig;
    }

    public void setResourceConfig(BaseResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
    }

    public BaseTargetConfig getTargetConfig() {
        return targetConfig;
    }

    public void setTargetConfig(BaseTargetConfig targetConfig) {
        this.targetConfig = targetConfig;
    }
}
