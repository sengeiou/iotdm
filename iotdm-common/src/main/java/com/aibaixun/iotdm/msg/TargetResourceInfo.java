package com.aibaixun.iotdm.msg;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;

import java.io.Serializable;

/**
 * 转发目标资源信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
public class TargetResourceInfo implements Serializable {


    public String id;

    public ResourceType resourceType;

    /**
     * 资源配置
     */
    private BaseResourceConfig resourceConfig;

    /**
     * 目标配置
     */
    private BaseTargetConfig targetConfig;


    /**
     * 规则名称
     */
    public String ruleLabel;

    /**
     * 资源名称
     */
    public String resourceLabel;


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

    public String getRuleLabel() {
        return ruleLabel;
    }

    public void setRuleLabel(String ruleLabel) {
        this.ruleLabel = ruleLabel;
    }

    public String getResourceLabel() {
        return resourceLabel;
    }

    public void setResourceLabel(String resourceLabel) {
        this.resourceLabel = resourceLabel;
    }
}
