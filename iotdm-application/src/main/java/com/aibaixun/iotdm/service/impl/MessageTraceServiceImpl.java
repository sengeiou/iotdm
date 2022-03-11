package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.mapper.MessageTraceMapper;
import com.aibaixun.iotdm.service.IMessageTraceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息追踪 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class MessageTraceServiceImpl extends ServiceImpl<MessageTraceMapper, MessageTraceEntity> implements IMessageTraceService {

    @Override
    public List<MessageTraceEntity> queryMessageTrace(String deviceId, Long ts) {

        LambdaQueryWrapper<MessageTraceEntity> q = Wrappers.<MessageTraceEntity>lambdaQuery()
                .eq(MessageTraceEntity::getDeviceId, deviceId)
                .ge(MessageTraceEntity::getCreateTime, ts)
                .orderByDesc(MessageTraceEntity::getCreateTime);
        return list(q);
    }


    @Override
    public void logDeviceMessageTrace(String deviceId, BusinessType businessType, BusinessStep businessStep, String businessDetails) {
        MessageTraceEntity messageTraceEntity = new MessageTraceEntity();
        messageTraceEntity.setDeviceId(deviceId);
        messageTraceEntity.setBusinessDetails(businessDetails);
        messageTraceEntity.setBusinessType(businessType);
        messageTraceEntity.setBusinessStep(businessStep);
        save(messageTraceEntity);
    }
}
