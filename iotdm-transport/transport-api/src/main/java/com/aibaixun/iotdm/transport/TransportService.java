package com.aibaixun.iotdm.transport;

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
     * @param deviceId 设备id  [主要防止缓存移除 但tcp 未断开]
     */
    void processDeviceDisConnect(UUID sessionId,String deviceId);


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

}
