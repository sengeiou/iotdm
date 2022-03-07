package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.RuleResource;
import com.aibaixun.iotdm.enums.ResourceType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 规则资源 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IRuleResourceService extends IService<RuleResource> {


    /**
     * 分页查询资源
     * @param page 页码
     * @param pageSize 页容
     * @param resourceType 资源类型
     * @return 资源分页
     */
    Page<RuleResource> pageQueryRuleResource (Integer page, Integer pageSize, ResourceType resourceType);

}
