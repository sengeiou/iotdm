package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.ForwardTarget;
import com.aibaixun.iotdm.support.RuleTargetResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 转发目标 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface ForwardTargetMapper extends BaseMapper<ForwardTarget> {


    /**
     * 统计 资源被 使用多少次
     * @param resourceId 资源id
     * @return 数目
     */
    Long countTargetByResourceId(@Param("resourceId") String resourceId);


    /**
     * 查询转发目标资源
     * @param ruleId 规则id
     * @return 转发目标资源
     */
    List<RuleTargetResource> selectRuleTargetAndResourceByRuleId (@Param("ruleId") String ruleId);

}
