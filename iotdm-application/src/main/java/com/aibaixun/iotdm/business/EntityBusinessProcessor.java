package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.enums.SendStatus;
import com.aibaixun.iotdm.event.ConfigRespEvent;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.queue.QueueSendServer;
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

    public void  doProcessEntityChangeEvent (EntityChangeEvent entityChangeEvent){
        queueSendService.sendEntityChangeData(entityChangeEvent);
    }


    public void  doProcessConfigRespEvent (ConfigRespEvent configRespEvent){
        deviceConfigSendService.updateDeviceConfigSend(configRespEvent.getDeviceId(), SendStatus.SUCCESS);
    }

    @Autowired
    public void setQueueSendService(QueueSendServer queueSendService) {
        this.queueSendService = queueSendService;
    }


    @Autowired
    public void setDeviceConfigSendService(IDeviceConfigSendService deviceConfigSendService) {
        this.deviceConfigSendService = deviceConfigSendService;
    }
}
