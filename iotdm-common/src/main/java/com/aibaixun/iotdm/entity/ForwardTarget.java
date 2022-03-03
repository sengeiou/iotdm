package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.ResourceType;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 转发目标
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_forward_target")
public class ForwardTarget extends BaseEntity {


    /**
     * 转发规则id
     */
    private String forwardRuleId;

    /**
     * 转发资源id
     */
    private String ruleResourceId;

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 转发参数
     */
    private String options;

    public String getForwardRuleId() {
        return forwardRuleId;
    }

    public void setForwardRuleId(String forwardRuleId) {
        this.forwardRuleId = forwardRuleId;
    }

    public String getRuleResourceId() {
        return ruleResourceId;
    }

    public void setRuleResourceId(String ruleResourceId) {
        this.ruleResourceId = ruleResourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "ForwardTarget{" +
                "forwardRuleId='" + forwardRuleId + '\'' +
                ", ruleResourceId='" + ruleResourceId + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}
