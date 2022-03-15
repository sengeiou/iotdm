package com.aibaixun.iotdm.transport.session;


/**
 * 连接session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface TransportSessionContext {

    /**
     * 下一条消息id
     * @return int
     */
    int nextMsgId();

}
