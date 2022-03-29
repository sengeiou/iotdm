package com.aibaixun.iotdm.business;

import com.aibaixun.basic.toolkit.HexTool;
import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.constants.TopicConstants;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.*;
import com.aibaixun.iotdm.script.JsInvokeService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;
import java.util.Objects;


/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Component
public class BusinessEventListener {

    private final Logger log = LoggerFactory.getLogger(BusinessEventListener.class);

    private JsInvokeService jsInvokeService;

    private BusinessReportProcessor<PrePropertyBusinessMsg,MessageBusinessMsg> matchProcessor;

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
        DataFormat dataFormat = devicePropertyUpEvent.getDataFormat();
        JsonNode jsonNode = null;
        matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.DEVICE_REPORT_DATA,payload,true);
        try {
            jsonNode = invokePluginMethod(dataFormat,payload, devicePropertyUpEvent.getProductId(), TopicConstants.PROPERTIES_UP);
            PrePropertyBusinessMsg prePropertyBusinessMsg = new PrePropertyBusinessMsg(new MetaData(devicePropertyUpEvent.getDeviceId(), devicePropertyUpEvent.getProductId()), jsonNode);
            matchProcessor.doProcessProperty(prePropertyBusinessMsg);
            matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.PLATFORM_RESOLVING_DATA,jsonNode !=null?jsonNode.toString():"{}",true);
        }catch (Exception e){
            log.error("DefaultIotDmEventListener.onDevicePropertyUpEvent >> is error ,message is:{},error is:{}",devicePropertyUpEvent,e.getMessage());
            matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.DEVICE_REPORT_DATA,e.getMessage(),false);
        }
    }



    @EventListener
    @Async("taskExecutor")
    public void onDeviceMessageUpEvent(DeviceMessageUpEvent deviceMessageUpEvent){
        log.info("DefaultIotDmEventListener.onDeviceMessageUpEvent >>  ,message is:{}",deviceMessageUpEvent);
        String payload = deviceMessageUpEvent.getPayload();
        DataFormat dataFormat = deviceMessageUpEvent.getDataFormat();
        matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.DEVICE_REPORT_DATA,payload,true);
        try {
            JsonNode jsonNode = invokePluginMethod(dataFormat,payload, deviceMessageUpEvent.getProductId(), TopicConstants.MESSAGE_UP);
            matchProcessor.doProcessMessage(new MessageBusinessMsg(new MetaData(deviceMessageUpEvent.getDeviceId(), deviceMessageUpEvent.getProductId()),jsonNode));
            matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.PLATFORM_RESOLVING_DATA,jsonNode !=null?jsonNode.toString():"{}",true);
        }catch (Exception e){
            log.error("DefaultIotDmEventListener.onDeviceMessageUpEvent >> is error ,message is:{},error is:{}",deviceMessageUpEvent,e.getMessage());
            matchProcessor.doProcessMessage(new MessageBusinessMsg(new MetaData(deviceMessageUpEvent.getDeviceId(), deviceMessageUpEvent.getProductId()),payload));
            matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.PLATFORM2DEVICE, BusinessStep.PLATFORM_RESOLVING_DATA,payload,false);
        }
    }




    @EventListener
    @Async("taskExecutor")
    public void onEntityChangeEvent(EntityChangeEvent entityChangeEvent){
        log.info("DefaultIotDmEventListener.onEntityChangeEvent >>  ,message is:{}",entityChangeEvent);
        entityProcessor.doProcessEntityChangeEvent(entityChangeEvent);
    }

    @EventListener
    @Async("taskExecutor")
    public void onConfigRespEvent(ConfigRespEvent configRespEvent){
        log.info("DefaultIotDmEventListener.onConfigRespEvent >>  ,message is:{}",configRespEvent);
        entityProcessor.doProcessConfigRespEvent(configRespEvent);
    }


    @EventListener
    @Async("taskExecutor")
    public void onOtaRespEvent(DeviceOtaRespEvent deviceOtaRespEvent){

    }



    @EventListener
    @Async("taskExecutor")
    public void onControlRespEvent(DeviceControlRespEvent deviceControlRespEvent){
        log.info("DefaultIotDmEventListener.onControlRespEvent >>  ,message is:{}",deviceControlRespEvent);
        String payload = deviceControlRespEvent.getPayload();
        DataFormat dataFormat = deviceControlRespEvent.getDataFormat();
        JsonNode jsonNode;
        try {
            jsonNode = invokePluginMethod(dataFormat,payload, deviceControlRespEvent.getProductId(), TopicConstants.CONTROL_RESP);
            if (Objects.nonNull(jsonNode)  ){
                int reqId = jsonNode.get("reqId").asInt();
                String deviceId = deviceControlRespEvent.getDeviceId();
                entityProcessor.doProcessControlRespEvent(deviceId,reqId);
            }
        }catch (Exception e){
            log.error("DefaultIotDmEventListener.onControlRespEvent >> is error ,message is:{},error is:{}",deviceControlRespEvent,e.getMessage());
        }
    }

    /**
     * 执行插件函数
     * @param dataFormat 数据格式
     * @param payload 负载内容
     * @param productId 产品id
     * @param topic 主题
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    private JsonNode invokePluginMethod (DataFormat dataFormat,String payload,String productId,String topic) throws ScriptException, NoSuchMethodException {
        JsonNode jsonNode = null;
        if (DataFormat.JSON.equals(dataFormat)){
            jsonNode = JsonUtil.parse(payload);
        }else if (DataFormat.BINARY.equals(dataFormat)){
            // todo 获取插件时候需要后期重构 设计思路采用工厂模式或者SPI
            byte [] messageBytes = HexTool.decodeHex(payload);
            String jsResult = (String)jsInvokeService.invokeDecodeFunction(productId, messageBytes, topic);
            jsonNode = JsonUtil.parse(jsResult);
        }
        return jsonNode;
    }


    @Autowired
    public void setJsInvokeService(JsInvokeService jsInvokeService) {
        this.jsInvokeService = jsInvokeService;
    }


    @Autowired
    public void setMatchProcessor(BusinessReportProcessor<PrePropertyBusinessMsg, MessageBusinessMsg> matchProcessor) {
        this.matchProcessor = matchProcessor;
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
