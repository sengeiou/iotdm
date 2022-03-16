package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.IotDmApplication;
import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IotDmApplication.class)
public class DevicePropertyReportTest {


    private IDevicePropertyReportService devicePropertyReportService;

    @Test
    public void testSaveProperty () {
        List<DevicePropertyReportEntity> devicePropertyReportEntities = new ArrayList<>(15);
        for (int i=0;i<10;i++){
            String s = String.valueOf(i % 2);
            devicePropertyReportEntities.add( new DevicePropertyReportEntity(s,i+"","abc",s));
        }

        new Thread(()->{
            devicePropertyReportService.saveOrUpdateBatch(devicePropertyReportEntities);
        }).start();
        new Thread(()->{
            devicePropertyReportService.saveOrUpdateBatch(devicePropertyReportEntities);
        }).start();
    }

    @Autowired
    public void setDevicePropertyReportService(IDevicePropertyReportService devicePropertyReportService) {
        this.devicePropertyReportService = devicePropertyReportService;
    }
}
