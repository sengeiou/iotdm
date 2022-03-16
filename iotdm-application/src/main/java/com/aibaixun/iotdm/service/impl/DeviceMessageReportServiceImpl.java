package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceMessageReportEntity;
import com.aibaixun.iotdm.mapper.DeviceMessageReportMapper;
import com.aibaixun.iotdm.service.IDeviceMessageReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 设备消息上报 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceMessageReportServiceImpl extends ServiceImpl<DeviceMessageReportMapper, DeviceMessageReportEntity> implements IDeviceMessageReportService {

    @Override
    public boolean saveOrUpdateBatch(Collection<DeviceMessageReportEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)){
            return false;
        }
        int size = entityList.size();
        return size == baseMapper.saveOrUpdateBatch(new ArrayList<>(entityList));
    }
}
