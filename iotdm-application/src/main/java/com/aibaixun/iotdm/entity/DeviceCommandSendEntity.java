package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.SendStatus;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 命令下发记录
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName(value = "t_device_command_send",autoResultMap = true)
public class DeviceCommandSendEntity extends BaseEntity {


    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 命令id
     */
    private String commandId;

    /**
     * 命令名称
     */
    private String commandLabel;

    /**
     * 参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String responses;

    /**
     * 下发时间
     */
    private Long ts;

    /**
     * 反馈时间
     */
    private Long respTs;

    /**
     * 请求id
     */
    private Integer reqId;

    /**
     * 发送状态
     */
    private SendStatus sendStatus;

    /**
     * mqtt 消息id
     */
    private Integer msgId;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getCommandLabel() {
        return commandLabel;
    }

    public void setCommandLabel(String commandLabel) {
        this.commandLabel = commandLabel;
    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Long getRespTs() {
        return respTs;
    }

    public void setRespTs(Long respTs) {
        this.respTs = respTs;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }



    @Override
    public String toString() {
        return "DeviceCommandSend{" +
                "deviceId='" + deviceId + '\'' +
                ", commandId='" + commandId + '\'' +
                ", commandLabel='" + commandLabel + '\'' +
                ", params=" + params +
                ", responses=" + responses +
                ", ts=" + ts +
                ", respTs=" + respTs +
                ", reqId=" + reqId +
                ", sendStatus=" + sendStatus +
                ", msgId=" + msgId +
                '}';
    }
}
