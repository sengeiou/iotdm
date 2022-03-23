package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DeviceCommandSendEntity;
import com.aibaixun.iotdm.enums.SendStatus;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 命令下发记录 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IDeviceCommandSendService extends IService<DeviceCommandSendEntity> {


    /**
     * 查询设备命令下发
     * @param deviceId 设备id
     * @param commandLabel 命令名称
     * @param commandId 命令id
     * @param startTs 开始时间
     * @param endTs 结束时间
     * @param page 限制条数
     * @param pageSize 限制条数
     * @return 命令下发历史
     */
    Page<DeviceCommandSendEntity> pageQueryDeviceCommandSend(String deviceId, String commandLabel, String commandId, Long startTs, Long endTs, Integer page,Integer pageSize);



    /**
     * 修改设备 命令
     * @param deviceId 设备id
     * @param msgId 消息id
     * @return 状态
     */
    Boolean updateDeviceCommandStatus2Received(String deviceId,Integer msgId);

    /**
     * 设置msg is
     * @param sendId 发送id
     * @param msgId 消息id
     * @return 修改结果
     */
    Boolean updateDeviceCommandToSetMsgId(Integer sendId, Integer msgId);


    /**
     * 修改设备
     * @param deviceId 设备id
     * @param reqId 请求id
     * @param targetStatus 目标状态
     * @return 修改结果
     */
    Boolean updateDeviceCommand(String deviceId, Integer reqId, SendStatus targetStatus);

}
