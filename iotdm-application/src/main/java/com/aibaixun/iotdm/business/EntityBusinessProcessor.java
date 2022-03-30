package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.enums.SendStatus;
import com.aibaixun.iotdm.event.DeviceConfigRespEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import com.aibaixun.iotdm.service.IDeviceConfigSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实体类处理类
 * @author Wang Xiao
 * @date 2022/3/30
 */
@Component
public class EntityBusinessProcessor extends AbstractBusinessProcessor{

    private QueueBusinessProcessor queueBusinessProcessor;

    private IDeviceConfigSendService deviceConfigSendService;

    private IDeviceCommandSendService deviceCommandSendService;

    /**
     * 处理实体更改
     * @param entityChangeEvent 实体更改
     */
    public void  doProcessEntityChangeEvent (EntityChangeEvent entityChangeEvent){
        queueBusinessProcessor.processEntityChangeData(entityChangeEvent);
    }


    /**
     * 处理 配置更改反馈
     * @param configRespEvent 配置更改反馈
     */
    public void  doProcessConfigRespEvent (DeviceConfigRespEvent configRespEvent){
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
    public void setQueueBusinessProcessor(QueueBusinessProcessor queueBusinessProcessor) {
        this.queueBusinessProcessor = queueBusinessProcessor;
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
