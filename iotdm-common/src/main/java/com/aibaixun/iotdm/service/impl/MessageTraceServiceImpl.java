package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.MessageTrace;
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
public class MessageTraceServiceImpl extends ServiceImpl<MessageTraceMapper, MessageTrace> implements IMessageTraceService {

    @Override
    public List<MessageTrace> queryMessageTrace(String deviceId, Long ts) {

        LambdaQueryWrapper<MessageTrace> q = Wrappers.<MessageTrace>lambdaQuery()
                .eq(MessageTrace::getDeviceId, deviceId)
                .ge(MessageTrace::getCreateTime, ts)
                .orderByDesc(MessageTrace::getCreateTime);
        return list(q);
    }
}
