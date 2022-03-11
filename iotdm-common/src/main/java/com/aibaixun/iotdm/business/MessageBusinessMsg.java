package com.aibaixun.iotdm.business;


/**
 * 消息 上报
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public class MessageBusinessMsg extends AbstractBusinessMsg{

    /**
     *  消息数据 不知道是否可以转换成json 或者使用二进制 报文，如果是二进制 会转换成16 进制的字符串标识
     */
    private Object  message;


    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public MessageBusinessMsg(MetaData metaData, Object message) {
        super(metaData);
        this.message = message;
    }
}
