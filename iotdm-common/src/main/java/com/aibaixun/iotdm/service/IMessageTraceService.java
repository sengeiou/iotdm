package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.MessageTrace;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 消息追踪 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IMessageTraceService extends IService<MessageTrace> {


    /**
     * 查询设备消息
     * @param deviceId 设备id
     * @param ts 时间
     * @return 消息数据
     */
    List<MessageTrace> queryMessageTrace (String deviceId,Long ts);

}
