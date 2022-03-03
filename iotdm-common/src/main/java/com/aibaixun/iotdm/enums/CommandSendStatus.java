package com.aibaixun.iotdm.enums;

/**
 * 命令发送结果
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public enum CommandSendStatus {

    /**
     * 已经发送
     */
    SEND,

    /**
     * 失败
     */
    FAIL,

    /**
     * 送达
     */
    SEND_ARRIVE,

    /**
     * 成功
     */
    SUCCESS;
}
