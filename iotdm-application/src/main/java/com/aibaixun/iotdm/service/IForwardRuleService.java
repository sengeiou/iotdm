package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
