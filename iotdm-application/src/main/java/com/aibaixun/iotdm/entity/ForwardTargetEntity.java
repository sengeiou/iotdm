package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.support.BaseTargetConfig;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 转发目标
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName(value = "t_forward_target",autoResultMap = true)
public class ForwardTargetEntity extends BaseEntity {


    /**
     * 转发规则id
     */
    @NotBlank(message = "转发规则id不允许为空")
    private String forwardRuleId;

    /**
     * 转发资源id
     */
    @NotBlank(message = "转发规则资源不允许为空")
    private String ruleResourceId;

    /**
     * 转发参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    @NotNull(message = "资源配置参数不允许为空")
    private BaseTargetConfig configuration;

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

    public BaseTargetConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(BaseTargetConfig configuration) {
        this.configuration = configuration;
    }


    @Override
    public String toString() {
        return "ForwardTarget{" +
                "forwardRuleId='" + forwardRuleId + '\'' +
                ", ruleResourceId='" + ruleResourceId + '\'' +
                ", configuration=" + configuration +
                '}';
    }
}
