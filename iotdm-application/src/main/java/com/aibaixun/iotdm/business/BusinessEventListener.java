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
        sessionProcessor.doProcessDeviceSessionEvent(deviceSessionEvent);
    }


    @EventListener
    @Async("taskExecutor")
    public void onDevicePropertyUpEvent(DevicePropertyUpEvent devicePropertyUpEvent){
        log.info(String.valueOf(devicePropertyUpEvent));
        String payload = devicePropertyUpEvent.getPayload();
        DataFormat dataFormat = devicePropertyUpEvent.getDataFormat();
        JsonNode jsonNode = null;
        matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.DEVICE_REPORT_DATA,payload,true);
        try {
            if (DataFormat.JSON.equals(dataFormat)){
                jsonNode = JsonUtil.parse(payload);
            }else if (DataFormat.BINARY.equals(dataFormat)){
                // todo 获取插件时候需要后期重构 设计思路采用工厂模式或者SPI
                byte [] messageBytes = HexTool.decodeHex(payload);
                String jsResult = (String)jsInvokeService.invokeDecodeFunction(devicePropertyUpEvent.getProductId(), messageBytes, TopicConstants.PROPERTIES_UP);
                jsonNode = JsonUtil.parse(jsResult);
            }
            PrePropertyBusinessMsg prePropertyBusinessMsg = new PrePropertyBusinessMsg(new MetaData(devicePropertyUpEvent.getDeviceId(), devicePropertyUpEvent.getProductId()), jsonNode);
            matchProcessor.doProcessProperty(prePropertyBusinessMsg);
            matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.PLATFORM_RESOLVING_DATA,jsonNode !=null?jsonNode.toString():"{}",true);
        }catch (Exception e){
            log.error("DefaultIotDmEventListener.onDevicePropertyUpEvent >> is error ,message is:{},error is:{}",devicePropertyUpEvent,e.getMessage());
            matchProcessor.doLog(devicePropertyUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.DEVICE_REPORT_DATA,e.getMessage(),false);
        }
    }



    @EventListener
    @Async("taskExecutor")
    public void onDeviceMessageUpEvent(DeviceMessageUpEvent deviceMessageUpEvent){
        log.info(String.valueOf(deviceMessageUpEvent));
        String payload = deviceMessageUpEvent.getPayload();
        matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.DEVICE_REPORT_DATA,payload,true);
        try {
            byte [] messageBytes = HexTool.decodeHex(payload);
            String jsResult = (String)jsInvokeService.invokeDecodeFunction(deviceMessageUpEvent.getProductId(), messageBytes, TopicConstants.MESSAGE_UP);
            JsonNode jsonNode = JsonUtil.parse(jsResult);
            matchProcessor.doProcessMessage(new MessageBusinessMsg(new MetaData(deviceMessageUpEvent.getDeviceId(), deviceMessageUpEvent.getProductId()),jsonNode));
            matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.PLATFORM_RESOLVING_DATA,jsonNode !=null?jsonNode.toString():"{}",true);
        }catch (Exception e){
            matchProcessor.doProcessMessage(new MessageBusinessMsg(new MetaData(deviceMessageUpEvent.getDeviceId(), deviceMessageUpEvent.getProductId()),payload));
            matchProcessor.doLog(deviceMessageUpEvent.getDeviceId(), BusinessType.DEVICE2PLATFORM, BusinessStep.PLATFORM_RESOLVING_DATA,payload,false);
        }
    }




    @EventListener
    @Async("taskExecutor")
    public void onEntityChangeEvent(EntityChangeEvent entityChangeEvent){
        entityProcessor.doProcessEntityChangeEvent(entityChangeEvent);
    }

    @EventListener
    @Async("taskExecutor")
    public void onConfigRespEvent(ConfigRespEvent configRespEvent){
        entityProcessor.doProcessConfigRespEvent(configRespEvent);
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
