package com.aibaixun.iotdm.transport.session;



import java.util.UUID;

/**
 * 设备 连接  session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public abstract class DeviceAwareSessionContext implements TransportSessionContext {


    protected final UUID sessionId;

    private volatile String deviceId;

    private volatile boolean connected;

    protected DeviceAwareSessionContext(UUID sessionId) {
        this.sessionId = sessionId;
    }


    public boolean isConnected() {
        return connected;
    }

    public void setDisconnected() {
        this.connected = false;
    }


    public void setConnected() {
        this.connected = true;
    }

    @Override
    public UUID getSessionId() {
        return sessionId;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



    //    protected volatile TransportDeviceInfo deviceInfo;
//
//
//    protected volatile DeviceProfile deviceProfile;
//
//    private volatile TransportProtos.SessionInfoProto sessionInfo;

    //    public DeviceId getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceInfo(TransportDeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//        this.deviceId = deviceInfo.getDeviceId();
//    }
//
//    @Override
//    public void onDeviceProfileUpdate(TransportProtos.SessionInfoProto sessionInfo, DeviceProfile deviceProfile) {
//        this.sessionInfo = sessionInfo;
//        this.deviceProfile = deviceProfile;
//        this.deviceInfo.setDeviceType(deviceProfile.getName());
//
//    }
//
//    @Override
//    public void onDeviceUpdate(TransportProtos.SessionInfoProto sessionInfo, Device device, Optional<DeviceProfile> deviceProfileOpt) {
//        this.sessionInfo = sessionInfo;
//        this.deviceInfo.setDeviceProfileId(device.getDeviceProfileId());
//        this.deviceInfo.setDeviceType(device.getType());
//        deviceProfileOpt.ifPresent(profile -> this.deviceProfile = profile);
//    }

}
