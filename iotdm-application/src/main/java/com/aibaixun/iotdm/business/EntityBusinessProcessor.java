package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.enums.SendStatus;
import com.aibaixun.iotdm.event.ConfigRespEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import com.aibaixun.iotdm.service.IDeviceConfigSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实体更改处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
public class EntityBusinessProcessor {

    private QueueSendServer queueSendService;

    private IDeviceConfigSendService deviceConfigSendService;

    private IDeviceCommandSendService deviceCommandSendService;

    /**
     * 处理实体更改
     * @param entityChangeEvent 实体更改
     */
    public void  doProcessEntityChangeEvent (EntityChangeEvent entityChangeEvent){
        queueSendService.sendEntityChangeData(entityChangeEvent);
    }


    /**
     * 处理 配置更改反馈
     * @param configRespEvent 配置更改反馈
     */
    public void  doProcessConfigRespEvent (ConfigRespEvent configRespEvent){
        deviceConfigSendService.updateDeviceConfigSend(configRespEvent.getDeviceId(), SendStatus.SUCCESS);
    }


    /**
     * 处理 配置更改反馈
     * @param deviceId 设备id
     * @param reqId 请求id
     */
    public void  doProcessControlRespEvent (String deviceId ,int reqId){
        deviceCommandSendService.updateDeviceCommand(deviceId,reqId,SendStatus.SUCCESS);
    }

    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }


    @Autowired
    public void setDeviceConfigSendService(IDeviceConfigSendService deviceConfigSendService) {
        this.deviceConfigSendService = deviceConfigSendService;
    }

    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }
}
