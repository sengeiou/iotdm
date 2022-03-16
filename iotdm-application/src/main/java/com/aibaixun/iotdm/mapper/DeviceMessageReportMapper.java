package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.DeviceMessageReportEntity;
import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备消息上报 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface DeviceMessageReportMapper extends BaseMapper<DeviceMessageReportEntity> {



    /**
     * 保存更新
     * @param devicePropertyReportEntities 设备属性信息
     * @return 数量
     */
    int saveOrUpdateBatch(@Param("entities") List<DeviceMessageReportEntity> devicePropertyReportEntities);

}
