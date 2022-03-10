package com.aibaixun.iotdm.event;

/**
 * 消息上报
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class DeviceMessageUpEvent extends BaseDataEvent{

    private String payload;

    public DeviceMessageUpEvent(String deviceId, String productId, String payload) {
        super(deviceId, productId);
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


    @Override
    public String toString() {
        return "DeviceMessageUpEvent{" +
                "baseData=" + super.toString()+
                "payload='" + payload + '\'' +
                '}';
    }
}
