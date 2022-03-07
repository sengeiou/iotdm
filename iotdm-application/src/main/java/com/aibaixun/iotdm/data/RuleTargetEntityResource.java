package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.entity.ForwardTargetEntity;
import com.aibaixun.iotdm.entity.RuleResourceEntity;

/**
 * 规则目标资源
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public class RuleTargetEntityResource extends ForwardTargetEntity {

    private RuleResourceEntity ruleResource;

    public RuleResourceEntity getRuleResource() {
        return ruleResource;
    }

    public void setRuleResource(RuleResourceEntity ruleResource) {
        this.ruleResource = ruleResource;
    }
}
