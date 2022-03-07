package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.RuleResource;
import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.mapper.RuleResourceMapper;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 规则资源 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class RuleResourceServiceImpl extends ServiceImpl<RuleResourceMapper, RuleResource> implements IRuleResourceService {


    @Override
    public Page<RuleResource> pageQueryRuleResource(Integer page, Integer pageSize, ResourceType resourceType) {

        LambdaQueryWrapper<RuleResource> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RuleResource::getTenantId, UserInfoUtil.getTenantIdOfNull());
        if (Objects.nonNull(resourceType)){
            queryWrapper.eq(RuleResource::getResourceType,resourceType);
        }
        queryWrapper.orderByDesc(RuleResource::getCreateTime);
        return page(Page.of(page,pageSize),queryWrapper);
    }
}
