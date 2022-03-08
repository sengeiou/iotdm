package com.aibaixun.iotdm.transport;

import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.TransportSessionInfo;

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
    void processDeviceConnectSuccess(TransportSessionInfo sessionInfo,TransportServiceCallback<Void> callback);


    /**
     * 注册session 元数据
     * @param transportSessionInfo session 信息
     * @param listener 监听者
     */
    void registerAsyncSession(TransportSessionInfo transportSessionInfo, TransportSessionListener listener);

}
