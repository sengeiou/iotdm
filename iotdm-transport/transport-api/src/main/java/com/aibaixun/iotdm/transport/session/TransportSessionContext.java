package com.aibaixun.iotdm.transport.session;


import java.util.UUID;

/**
 * 连接session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface TransportSessionContext {

    /**
     * 获取连接session id
     * @return uuid
     */
    UUID getSessionId ();


    /**
     * 下一条消息id
     * @return int
     */
    int nextMsgId();

    /**
     * 设备id
     * @return String
     */
    String getDeviceId();


    /**
     * 产品id
     * @return String
     */
    String getProductId();
}
