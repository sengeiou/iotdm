package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.DeviceConfigSendEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public interface DeviceConfigSendEntityMapper extends BaseMapper<DeviceConfigSendEntity> {

    /**
     * 保存或更新数据
     * @param entity deviceConfigSendEntity
     */
    void saveOrUpdateConfigSend(@Param("entity") DeviceConfigSendEntity entity);
}
