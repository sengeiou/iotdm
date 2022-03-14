package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.msg.SessionEventType;

import javax.xml.stream.events.EntityDeclaration;

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
    void  publishConfigOtaRespUpEvent(String productId,String deviceId,  DataFormat dataFormat, String payload);



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
}
