package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.MessageTraceEntity;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface IMessageTraceService extends IService<MessageTraceEntity> {


    /**
     * 查询设备消息
     * @param deviceId 设备id
     * @param businessType 业务类型
     * @param messageStatus 消息状态
     * @param debugDevice 是否设备在线调试
     * @return 消息数据
     */
    List<MessageTraceEntity> queryMessageTrace (String deviceId,BusinessType businessType,Boolean messageStatus,Boolean debugDevice);



    /**
     * 查询设备消息
     * @param deviceId 设备id
     * @param businessType 业务类型
     * @param messageStatus 消息状态
     * @param page 分页
     * @param pageSize 分页
     * @return 消息数据
     */
    Page<MessageTraceEntity> pageQueryMessageTrace (String deviceId, BusinessType businessType, Boolean messageStatus, Integer page, Integer pageSize);


    /**
     * 记录追踪数据
     * @param deviceId 设备id
     * @param businessType 业务类型
     * @param businessStep 业务步骤
     * @param businessDetails 业务细节
     * @param messageStatus 消息状态
     */
    void logDeviceMessageTrace (String deviceId, BusinessType businessType, BusinessStep businessStep, String businessDetails,Boolean messageStatus);


    /**
     * 移除日志
     * @param deviceId 设备id
     */
    void  removeDeviceMessage(String deviceId);

}
