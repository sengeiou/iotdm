package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 规则状态更改参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public class RuleStatusParam {

    @NotNull(message = "转发规则目标状态不允许为空")
    private Boolean status;


    @NotBlank(message = "转发规则id不允许为空")
    private String forwardRuleId;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getForwardRuleId() {
        return forwardRuleId;
    }

    public void setForwardRuleId(String forwardRuleId) {
        this.forwardRuleId = forwardRuleId;
    }

    @Override
    public String toString() {
        return "RuleStatusParam{" +
                "status=" + status +
                ", forwardRuleId='" + forwardRuleId + '\'' +
                '}';
    }
}
