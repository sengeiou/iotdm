package com.aibaixun.iotdm.rule.server;

import com.aibaixun.iotdm.msg.ForwardRuleInfo;

import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
public interface RuleService {


    /**
     * 查询转发规则
     * @param tenantId 租户id
     * @return 转发规则
     */
    List<ForwardRuleInfo> queryForwardRule (String tenantId);

    /**
     * 查询设备租户id
     * @param productId 设备id
     * @return 设备id
     */
    String getCurrentProductTenantId (String productId);
}
