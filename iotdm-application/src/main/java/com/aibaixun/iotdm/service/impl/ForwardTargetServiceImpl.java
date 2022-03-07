package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ForwardTargetEntity;
import com.aibaixun.iotdm.mapper.ForwardTargetMapper;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.data.RuleTargetEntityResource;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 转发目标 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ForwardTargetServiceImpl extends ServiceImpl<ForwardTargetMapper, ForwardTargetEntity> implements IForwardTargetService {


    @Override
    public Long countTargetByResourceId(String resourceId) {
        return baseMapper.countTargetByResourceId(resourceId);
    }


    @Override
    public List<RuleTargetEntityResource> listQueryRuleTargetAndResource(String ruleId) {
        return baseMapper.selectRuleTargetAndResourceByRuleId(ruleId);
    }

    @Override
    public Long countTargetByRuleId(String ruleId) {
        return count(Wrappers.<ForwardTargetEntity>lambdaQuery().eq(ForwardTargetEntity::getForwardRuleId,ruleId));
    }
}
