package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 转发规则
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_forward_rule")
public class ForwardRule extends BaseEntity {

    /**
     * 规则名称
     */
    private String ruleLabel;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据来源
     */
    private String subjectResource;

    /**
     * 触发事件
     */
    private String subjectEvent;

    /**
     * 规则状态
     */
    private Boolean ruleStatus;

    /**
     * 是否删除
     */
    private Boolean deleted;


    public String getRuleLabel() {
        return ruleLabel;
    }

    public void setRuleLabel(String ruleLabel) {
        this.ruleLabel = ruleLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectResource() {
        return subjectResource;
    }

    public void setSubjectResource(String subjectResource) {
        this.subjectResource = subjectResource;
    }

    public String getSubjectEvent() {
        return subjectEvent;
    }

    public void setSubjectEvent(String subjectEvent) {
        this.subjectEvent = subjectEvent;
    }

    public Boolean getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Boolean ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ForwardRule{" +
                "ruleLabel='" + ruleLabel + '\'' +
                ", description='" + description + '\'' +
                ", subjectResource='" + subjectResource + '\'' +
                ", subjectEvent='" + subjectEvent + '\'' +
                ", ruleStatus=" + ruleStatus +
                ", deleted=" + deleted +
                '}';
    }
}
