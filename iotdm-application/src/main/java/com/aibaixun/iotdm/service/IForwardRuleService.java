package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 转发规则 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IForwardRuleService extends IService<ForwardRuleEntity> {


    /**
     * 分页查询转发 规则
     * @param page 页码
     * @param pageSize 页容
     * @param ruleLabel 规则名称
     * @return 转发规则
     */
    Page<ForwardRuleEntity> pageQueryForwardRule(Integer page, Integer pageSize, String ruleLabel);




    /**
     * 更改 规则状态
     * @param id id
     * @param status 状态
     * @return 修改标识
     */
    Boolean updateRuleStatus (String id,Boolean status);

    /**
     * 修改 规则
     * @param id id
     * @param ruleLabel 名称
     * @param description 描述
     * @return 修改标识
     */
    Boolean updateRuleEntity (String id,String ruleLabel,String description);


    /**
     *  查询租户 转发规则
     * @param tenantId 租户id
     * @return 转发规则
     */
    List<ForwardRuleInfo> queryForwardRuleByTenantId(String tenantId);

    /**
     * 统计规则
     * @return 数目
     */
    Long countRule();

    /**
     * 规则名称查询
     * @param ruleLabel 规则名称
     * @return 规则
     */
    Long countByRuleLabel(String ruleLabel);
}
