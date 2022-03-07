package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.entity.ForwardTarget;
import com.aibaixun.iotdm.entity.RuleResource;

/**
 * 规则目标资源
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public class RuleTargetResource extends ForwardTarget {

    private RuleResource ruleResource;

    public RuleResource getRuleResource() {
        return ruleResource;
    }

    public void setRuleResource(RuleResource ruleResource) {
        this.ruleResource = ruleResource;
    }
}
