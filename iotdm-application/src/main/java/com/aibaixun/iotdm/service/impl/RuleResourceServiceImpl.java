package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.RuleResourceEntity;
import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.mapper.RuleResourceMapper;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class RuleResourceServiceImpl extends ServiceImpl<RuleResourceMapper, RuleResourceEntity> implements IRuleResourceService {


    @Override
    public Page<RuleResourceEntity> pageQueryRuleResource(Integer page, Integer pageSize, String resourceLabel) {

        LambdaQueryWrapper<RuleResourceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(RuleResourceEntity::getResourceLabel,RuleResourceEntity::getId,RuleResourceEntity::getResourceStatus,RuleResourceEntity::getResourceType);
        queryWrapper.eq(RuleResourceEntity::getTenantId, UserInfoUtil.getTenantIdOfNull());
        if (Objects.nonNull(resourceLabel)){
            queryWrapper.likeRight(RuleResourceEntity::getResourceLabel,resourceLabel);
        }
        queryWrapper.orderByDesc(RuleResourceEntity::getCreateTime);
        return page(Page.of(page,pageSize),queryWrapper);
    }


    @Override
    public List<RuleResourceEntity> listQueryRuleResource(Integer limit, String resourceLabel, ResourceType resourceType) {
        LambdaQueryWrapper<RuleResourceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(RuleResourceEntity::getResourceLabel,RuleResourceEntity::getId,RuleResourceEntity::getResourceStatus,RuleResourceEntity::getResourceType);
        queryWrapper.eq(RuleResourceEntity::getTenantId, UserInfoUtil.getTenantIdOfNull()).eq(RuleResourceEntity::getResourceStatus,true);
        if (StringUtils.isNotBlank(resourceLabel)){
            queryWrapper.likeRight(RuleResourceEntity::getResourceLabel,resourceLabel);
        }
        if (Objects.nonNull(resourceType)){
            queryWrapper.eq(RuleResourceEntity::getResourceType,resourceType);
        }

        queryWrapper.orderByDesc(RuleResourceEntity::getCreateTime);
        queryWrapper.last(" LIMIT "+ limit);
        return list(queryWrapper);
    }

    @Override
    public Boolean updateResourceStatus(String resourceId, Boolean resourceStatus) {
        LambdaUpdateWrapper<RuleResourceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(RuleResourceEntity::getResourceStatus,resourceStatus).eq(RuleResourceEntity::getId,resourceId);
        return update(updateWrapper);
    }

    @Override
    public Long countResource() {
        return count(Wrappers.<RuleResourceEntity>lambdaQuery().eq(RuleResourceEntity::getTenantId,UserInfoUtil.getTenantIdOfNull()));
    }
}
