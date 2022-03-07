package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ForwardTargetEntity;
import com.aibaixun.iotdm.data.RuleTargetEntityResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 转发目标 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IForwardTargetService extends IService<ForwardTargetEntity> {


    /**
     * 统计 资源被 使用多少次
     * @param resourceId 资源id
     * @return 数目
     */
    Long countTargetByResourceId(String resourceId);


    /**
     * 查询规则 转发目标资源
     * @param ruleId 规则id
     * @return 转发目标资源
     */
    List<RuleTargetEntityResource> listQueryRuleTargetAndResource (String ruleId);


    /**
     * 统计设备 规则id
     * @param ruleId  规则id
     * @return 转发目标数目
     */
    Long countTargetByRuleId (String ruleId);


}
