package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.constants.TopicConstants;
import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 业务 时间处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Component
public class BusinessEventListener {

    private final Logger log = LoggerFactory.getLogger(BusinessEventListener.class);


    private PlugBusinessProcessor plugBusinessProcessor;

    private SessionBusinessProcessor sessionProcessor;

    private EntityBusinessProcessor entityProcessor;


    @EventListener
    @Async("taskExecutor")
    public void onDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        log.info("DefaultIotDmEventListener.onDeviceSessionEvent >>  ,message is:{}",deviceSessionEvent);
        sessionProcessor.doProcessDeviceSessionEvent(deviceSessionEvent);
    }


    @EventListener
    @Async("taskExecutor")
    public void onDevicePropertyUpEvent(DevicePropertyUpEvent devicePropertyUpEvent){
        log.info("DefaultIotDmEventListener.onDevicePropertyUpEvent >>  ,message is:{}",devicePropertyUpEvent);
        String payload = devicePropertyUpEvent.getPayload();
        String deviceId = devicePropertyUpEvent.getDeviceId();
        String productId = devicePropertyUpEvent.getProductId();
        DataFormat dataFormat = devicePropertyUpEvent.getDataFormat();
        plugBusinessProcessor.processReport(dataFormat,payload,deviceId,productId,true);
    }



    @EventListener
    @Async("taskExecutor")
    public void onDeviceMessageUpEvent(DeviceMessageUpEvent deviceMessageUpEvent){
        log.info("DefaultIotDmEventListener.onDeviceMessageUpEvent >>  ,message is:{}",deviceMessageUpEvent);
        String payload = deviceMessageUpEvent.getPayload();
        String deviceId = deviceMessageUpEvent.getDeviceId();
        String productId = deviceMessageUpEvent.getProductId();
        DataFormat dataFormat = deviceMessageUpEvent.getDataFormat();
        plugBusinessProcessor.processReport(dataFormat,payload,deviceId,productId,false);
    }




    @EventListener
    @Async("taskExecutor")
    public void onEntityChangeEvent(EntityChangeEvent entityChangeEvent){
        log.info("DefaultIotDmEventListener.onEntityChangeEvent >>  ,message is:{}",entityChangeEvent);
        entityProcessor.doProcessEntityChangeEvent(entityChangeEvent);
    }

    @EventListener
    @Async("taskExecutor")
    public void onConfigRespEvent(DeviceConfigRespEvent configRespEvent){
        log.info("DefaultIotDmEventListener.onConfigRespEvent >>  ,message is:{}",configRespEvent);
        entityProcessor.doProcessConfigRespEvent(configRespEvent);
    }


    @EventListener
    @Async("taskExecutor")
    public void onOtaRespEvent(DeviceOtaRespEvent deviceOtaRespEvent){
        log.info("DefaultIotDmEventListener.onOtaRespEvent >>  ,message is:{}",deviceOtaRespEvent);
        String payload = deviceOtaRespEvent.getPayload();
        DataFormat dataFormat = deviceOtaRespEvent.getDataFormat();
        String productId = deviceOtaRespEvent.getProductId();
        JsonNode jsonNode = plugBusinessProcessor.doInvokePluginMethod(dataFormat,payload,productId,TopicConstants.OTA_RESP);
        log.info("DefaultIotDmEventListener.onOtaRespEvent >>  ,message is:{}",jsonNode);
    }



    @EventListener
    @Async("taskExecutor")
    public void onControlRespEvent(DeviceControlRespEvent deviceControlRespEvent){
        log.info("DefaultIotDmEventListener.onControlRespEvent >>  ,message is:{}",deviceControlRespEvent);
        String payload = deviceControlRespEvent.getPayload();
        DataFormat dataFormat = deviceControlRespEvent.getDataFormat();
        String productId = deviceControlRespEvent.getProductId();
        JsonNode jsonNode = plugBusinessProcessor.doInvokePluginMethod(dataFormat,payload,productId,TopicConstants.CONTROL_RESP);
        if (Objects.nonNull(jsonNode)  ){
            int reqId = jsonNode.get("reqId").asInt();
            String deviceId = deviceControlRespEvent.getDeviceId();
            entityProcessor.doProcessControlRespEvent(deviceId,reqId);
        }
    }



    @Autowired
    public void setPlugBusinessProcessor(PlugBusinessProcessor plugBusinessProcessor) {
        this.plugBusinessProcessor = plugBusinessProcessor;
    }

    @Autowired
    public void setEntityProcessor(EntityBusinessProcessor entityProcessor) {
        this.entityProcessor = entityProcessor;
    }

    @Autowired
    public void setSessionProcessor(SessionBusinessProcessor sessionProcessor) {
        this.sessionProcessor = sessionProcessor;
    }
}
