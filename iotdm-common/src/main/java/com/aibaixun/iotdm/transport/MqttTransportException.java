package com.aibaixun.iotdm.transport;

/**
 * 传输层特定异常  返回信息中包含 连接不成功code
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public class MqttTransportException extends RuntimeException {

    /**
     * mqtt 错误码
     */
    private final byte returnCode;

    public MqttTransportException(byte returnCode) {
        this.returnCode = returnCode;
    }


    public byte getReturnCode() {
        return returnCode;
    }
}
