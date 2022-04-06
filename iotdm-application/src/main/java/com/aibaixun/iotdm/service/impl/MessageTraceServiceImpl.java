package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.mapper.MessageTraceMapper;
import com.aibaixun.iotdm.service.IMessageTraceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

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
    public List<MessageTraceEntity> queryMessageTrace(String deviceId,  BusinessType businessType, Boolean messageStatus,Boolean debugDevice) {
        LambdaQueryWrapper<MessageTraceEntity> queryWrapper = getQueryWrapper(deviceId, businessType, messageStatus,debugDevice);
        queryWrapper.last(" LIMIT 20");
        return list(queryWrapper);
    }


    @Override
    public Page<MessageTraceEntity> pageQueryMessageTrace(String deviceId, BusinessType businessType, Boolean messageStatus, Integer page, Integer pageSize) {
        LambdaQueryWrapper<MessageTraceEntity> queryWrapper = getQueryWrapper(deviceId, businessType, messageStatus,false);
        return page(Page.of(page,pageSize),queryWrapper);
    }

    private LambdaQueryWrapper<MessageTraceEntity> getQueryWrapper(String deviceId, BusinessType businessType, Boolean messageStatus,Boolean debugDevice) {
        LambdaQueryWrapper<MessageTraceEntity> queryWrapper = Wrappers.<MessageTraceEntity>lambdaQuery()
                .eq(MessageTraceEntity::getDeviceId, deviceId)
                .orderByDesc(MessageTraceEntity::getCreateTime);
        if (Objects.nonNull(businessType)){
            queryWrapper.eq(MessageTraceEntity::getBusinessType,businessType);
        }
        if (debugDevice){
            // 在线调试查看5秒内数据
            queryWrapper.ge(MessageTraceEntity::getCreateTime,Instant.now().toEpochMilli()-8000);
        }
        if (Objects.nonNull(messageStatus)){
            queryWrapper.eq(MessageTraceEntity::getMessageStatus,messageStatus);
        }
        return queryWrapper;
    }

    @Override
    public void logDeviceMessageTrace(String deviceId, BusinessType businessType, BusinessStep businessStep, String businessDetails,Boolean messageStatus) {
        MessageTraceEntity messageTraceEntity = new MessageTraceEntity();
        messageTraceEntity.setDeviceId(deviceId);
        messageTraceEntity.setBusinessDetails(businessDetails);
        messageTraceEntity.setBusinessType(businessType);
        messageTraceEntity.setBusinessStep(businessStep);
        messageTraceEntity.setMessageStatus(messageStatus);
        save(messageTraceEntity);
    }

    @Override
    public void removeDeviceMessage(String deviceId) {
        remove(Wrappers.<MessageTraceEntity>lambdaQuery().eq(MessageTraceEntity::getDeviceId, deviceId));
    }
}
