package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.SubjectEvent;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 转发规则
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName(value = "t_forward_rule",autoResultMap = true)
public class ForwardRuleEntity extends BaseEntity {

    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不允许为空")
    private String ruleLabel;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据来源
     */
    @NotNull(message = "规则的数据来源不允许为空")
    private String subjectResource;

    /**
     * 触发事件
     */
    @NotNull(message = "规则的触发事件不允许为空")
    private SubjectEvent subjectEvent;

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

    public SubjectEvent getSubjectEvent() {
        return subjectEvent;
    }

    public void setSubjectEvent(SubjectEvent subjectEvent) {
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
                ", subjectEvent=" + subjectEvent +
                ", ruleStatus=" + ruleStatus +
                ", deleted=" + deleted +
                '}';
    }
}
