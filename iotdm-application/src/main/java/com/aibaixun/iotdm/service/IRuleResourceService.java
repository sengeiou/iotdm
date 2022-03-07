package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.RuleResourceEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 规则资源 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IRuleResourceService extends IService<RuleResourceEntity> {


    /**
     * 分页查询资源
     * @param page 页码
     * @param pageSize 页容
     * @param resourceLabel 资源名称
     * @return 资源分页
     */
    Page<RuleResourceEntity> pageQueryRuleResource (Integer page, Integer pageSize, String resourceLabel);


    /**
     * 查询 资源列表
     * @param limit 限制数目
     * @param resourceLabel 资源名称
     * @return 资源列表
     */
    List<RuleResourceEntity> listQueryRuleResource (Integer limit, String resourceLabel);

}
