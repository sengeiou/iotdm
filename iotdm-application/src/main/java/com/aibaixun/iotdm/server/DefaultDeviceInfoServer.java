package com.aibaixun.iotdm.server;

import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.DeviceInfo;
import com.aibaixun.iotdm.service.*;
import com.aibaixun.iotdm.transport.MqttTransportException;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

/**
 * 设备信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Service
public class DefaultDeviceInfoServer extends BaseSqlInfoServer implements DeviceInfoServer {

    private IDeviceService deviceService;

    private IProductService productService;

    private IDeviceCommandSendService deviceCommandSendService;

    @Override
    public ListenableFuture<DeviceInfo> mqttDeviceAuthBySecret(DeviceAuthSecretReqMsg deviceAuthSecretReqMsg) {
        return sqlExecutorService.submit(()->{
            DeviceEntity deviceEntity = deviceService.queryBy3Param(deviceAuthSecretReqMsg.getClientId(), deviceAuthSecretReqMsg.getUsername(), deviceAuthSecretReqMsg.getPassword());
            if (Objects.isNull(deviceEntity)){
                throw new MqttTransportException((byte)4);
            }
            return toData(deviceEntity);
        });
    }



    private DeviceInfo toData (DeviceEntity deviceEntity){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId(deviceEntity.getId());
        deviceInfo.setDeviceCode(deviceEntity.getDeviceCode());
        deviceInfo.setProductId(deviceEntity.getProductId());
        if (StringUtils.isNotBlank(deviceEntity.getProductId())){
            ProductEntity product = productService.getById(deviceEntity.getProductId());
            if (Objects.nonNull(product)){
                deviceInfo.setProtocolType(product.getProtocolType());
                deviceInfo.setDataFormat(product.getDataFormat());
            }
        }
        return deviceInfo;
    }


    @Override
    public ListenableFuture<Boolean> setDeviceStatus2OnLine(String deviceId) {
        return sqlExecutorService.submit(()->deviceService.updateDeviceStatus(deviceId, DeviceStatus.ONLINE, Instant.now().toEpochMilli(),null,null));
    }




    @Override
    public ListenableFuture<Boolean> setDeviceStatus2OffOnLine(String deviceId, Long lastConnectTime, Long lastActivityTime, String hostName) {
        return sqlExecutorService.submit(()->deviceService.updateDeviceStatus(deviceId, DeviceStatus.OFFLINE,lastConnectTime,lastActivityTime,hostName));
    }

    @Override
    public ListenableFuture<Boolean> setDeviceStatus2Warn(String deviceId) {
        return sqlExecutorService.submit(()->deviceService.updateDeviceStatus(deviceId, DeviceStatus.WARN,null,null,null));
    }


    @Override
    public ListenableFuture<Boolean> toDeviceMessageIsReceived(String deviceId, int msgId) {
        return sqlExecutorService.submit(()->deviceCommandSendService.updateDeviceCommandStatus2Received(deviceId, msgId));
    }

    @Override
    public void setControlMsgId(Integer sendId, Integer msgId) {
        sqlExecutorService.submit(()->deviceCommandSendService.updateDeviceCommandToSetMsgId(sendId, msgId));
    }

    @Override
    public void onRedisExpirationMessage(String redisKey) {
        String[] splitStr = redisKey.split(":");
        int keyMinLength = 4;
        if (splitStr.length!= keyMinLength){
            return;
        }
        String deviceId = splitStr[3];
        setDeviceStatus2OffOnLine(deviceId,null, Instant.now().toEpochMilli()-1000*60,null);
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }


    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }
}
