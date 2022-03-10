package com.aibaixun.iotdm.msg;

/**
 * session 事件
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public enum SessionEventType {

    /**
     * 连接
     */
    CONNECT,
    /**
     * 断开
     */
    DISCONNECT,
    /**
     * 预警
     */
    WARN;
}
