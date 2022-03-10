package com.aibaixun.iotdm.transport;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.TransportSessionInfo;

import java.util.UUID;

/**
 * 数据传输 service
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public interface TransportService {

    /**
     * 设备密钥认证
     * @param protocolType 设备协议
     * @param deviceAuthSecretReqMsg 设备密钥认证消息
     * @param callback 回调服务
     */
    void  processDeviceAuthBySecret(ProtocolType protocolType, DeviceAuthSecretReqMsg deviceAuthSecretReqMsg,TransportServiceCallback<DeviceAuthRespMsg> callback);


    /**
     * 设备连接成功
     * @param sessionInfo 会话信息
     * @param callback 回调函数
     */
    void processDeviceConnectSuccess(TransportSessionInfo sessionInfo,TransportServiceCallback<Boolean> callback);


    /**
     * 设备断开连接
     * @param sessionId 会话信息
     * @param productId 产品id  [主要防止缓存移除 但tcp 未断开]
     * @param deviceId 设备id  [主要防止缓存移除 但tcp 未断开]
     * @param hostName 连接地址
     */
    void processDeviceDisConnect(UUID sessionId,String productId,String deviceId,String hostName);


    /**
     * 记录设备信息
     * @param sessionId session 信息
     * @param hostName 连接地址
     * @param deviceId 设备id
     */
    void processLogDevice(UUID sessionId,String deviceId,String hostName);


    /**
     * 注册session 元数据
     * @param transportSessionInfo session 信息
     * @param listener 监听者
     */
    void registerSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener);


    /**
     * 移除 session
     * @param deviceId 设备id
     * @param sessionId session 信息
     */
    void deregisterSession(UUID sessionId,String deviceId);


    /**
     * 记录活跃时间
     * @param deviceId 设备id
     * @param sessionId session 信息
     */
    void reportActivity(UUID sessionId,String deviceId);


    /**
     * 设备上报属性信息
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param dataFormat 数据格式
     * @param callback 回调函数
     */
    void  processPropertyUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat,String payload,TransportServiceCallback<Void> callback);


    /**
     * 设备上报消息信息
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param callback 回调函数
     */
    void  processMessageUp(UUID sessionId, String deviceId, String productId,String payload,TransportServiceCallback<Void> callback);


    /**
     * 设备上报预警信息
     * @param sessionId session 信息
     * @param deviceId 设备id
     * @param productId 产品id
     * @param callback 回调函数
     */
    void  processWarnUp(UUID sessionId, String deviceId, String productId,TransportServiceCallback<Void> callback);

    /**
     * 设备上报消息收到消息
     * @param sessionId session 信息
     * @param deviceId 设备id
     * @param msgId 消息id
     */
    void  processPubAck(UUID sessionId,String deviceId,int msgId);


    /**
     * 设备上报配置更改反馈
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param dataFormat 数据格式
     * @param callback 回调函数
     */
    void  processConfigRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat,String payload,TransportServiceCallback<Void> callback);

    /**
     * 设备上报OTA更改反馈
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param dataFormat 数据格式
     * @param callback 回调函数
     */
    void  processOtaRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat,String payload,TransportServiceCallback<Void> callback);

    /**
     * 设备上报命令执行反馈
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param dataFormat 数据格式
     * @param callback 回调函数
     */
    void  processControlRespUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat,String payload,TransportServiceCallback<Void> callback);


    /**
     * 设备上报命令请求
     * @param sessionId session 信息
     * @param payload 负载内容
     * @param deviceId 设备id
     * @param productId 产品id
     * @param dataFormat 数据格式
     * @param callback 回调函数
     */
    void  processControlReqUp(UUID sessionId, String deviceId, String productId, DataFormat dataFormat,String payload,TransportServiceCallback<Void> callback);

}
