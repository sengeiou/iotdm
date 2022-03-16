package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.*;
import com.aibaixun.iotdm.msg.SessionEventType;
import com.aibaixun.iotdm.transport.SessionId;

/**
 * 事件发布者
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public interface IotDmEventPublisher {

    /**
     * 发布设备连接事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param sessionEventType 设备事件类型
     */
    void  publishDeviceSessionEvent(String productId, String deviceId, SessionEventType sessionEventType);


    /**
     * 属性发布事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param dataFormat 数据格式
     * @param payload 负载
     */
    void publishPropertyUpEvent(String productId,String deviceId,  DataFormat dataFormat, String payload);



    /**
     * 消息发布事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param payload 负载
     */
    void publishMessageUpEvent(String productId,String deviceId, String payload);


    /**
     * 发布设备配置更改修改反馈事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param dataFormat 数据格式
     * @param payload 负载
     */
    void  publishConfigRespUpEvent(String productId,String deviceId,  DataFormat dataFormat, String payload);



    /**
     * 发布设备ota反馈事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param dataFormat 数据格式
     * @param payload 负载
     */
    void publishOtaRespUpEvent(String productId, String deviceId, DataFormat dataFormat, String payload);



    /**
     * 发布设备命令反馈事件
     * @param productId 产品id
     * @param deviceId 设备id
     * @param dataFormat 数据格式
     * @param payload 负载
     */
    void  publishControlRespEvent(String productId,String deviceId,  DataFormat dataFormat, String payload);

    /**
     * 发布设备命令请求
     * @param productId 产品id
     * @param deviceId 设备id
     * @param dataFormat 数据格式
     * @param payload 负载
     */
    void  publishControlReqEvent(String productId,String deviceId,  DataFormat dataFormat, String payload);

    /**
     * 发布实体更改
     * @param entityChangeEvent entityChangeEvent
     */
    void  publishEntityChangeEvent(EntityChangeEvent entityChangeEvent);


    /**
     * 设备配置 更改去
     * @param  deviceConfigEvent 设备配置更改事件
     */
    void publish2DeviceConfigReqEvent(ToDeviceConfigEvent deviceConfigEvent);

    /**
     * 设备配置更改
     * @param  deviceOtaEvent 设备固件事件
     */
    void publish2DeviceOtaReqEvent(ToDeviceOtaEvent deviceOtaEvent);

    /**
     * 设备命令事件
     * @param deviceControlEvent 设备命令事件
     */
    void publish2ControlReqEvent(ToDeviceControlEvent deviceControlEvent);

    /**
     * 发布设备关闭
     * @param deviceCloseConnectEvent 设备关闭
     */
    void publishDeviceCloseConnectEvent(ToDeviceCloseConnectEvent deviceCloseConnectEvent);
}
