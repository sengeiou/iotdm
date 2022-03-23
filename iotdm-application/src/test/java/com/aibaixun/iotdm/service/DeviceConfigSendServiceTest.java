package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.IotDmApplication;
import com.aibaixun.iotdm.entity.DeviceConfigSendEntity;
import com.aibaixun.iotdm.enums.SendStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IotDmApplication.class)
public class DeviceConfigSendServiceTest {

    @Autowired
    private IDeviceConfigSendService deviceConfigSendService;

    @Test
    public  void testSaveOrUpdate() {
        DeviceConfigSendEntity deviceConfigSendEntity = new DeviceConfigSendEntity();
        deviceConfigSendEntity.setDeviceId("deviceId");
        deviceConfigSendEntity.setPayload("payload");
        deviceConfigSendEntity.setSendStatus(SendStatus.SEND);
        deviceConfigSendService.saveOrUpdateConfigSend(deviceConfigSendEntity);
    }


}
