package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 转发规则 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface ForwardRuleMapper extends BaseMapper<ForwardRuleEntity> {

    /**
     *  查询租户 转发规则
     * @param tenantId 租户id
     * @return 转发规则
     */
    List<ForwardRuleInfo> selectForwardRuleByTenantId(@Param("tenantId") String tenantId);
}
