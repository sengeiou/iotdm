package com.aibaixun.iotdm.business;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.DeviceMessageUpEvent;
import com.aibaixun.iotdm.event.DevicePropertyUpEvent;
import com.aibaixun.iotdm.event.DeviceSessionEvent;
import com.aibaixun.iotdm.script.JsInvokeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class DefaultIotDmEventListener {

    private final Logger log = LoggerFactory.getLogger(DefaultIotDmEventListener.class);

    private JsInvokeService jsInvokeService;


    @EventListener
    @Async("taskExecutor")
    public void onDeviceSessionEvent(DeviceSessionEvent deviceSessionEvent){
        log.info(String.valueOf(deviceSessionEvent));
    }


    @EventListener
    @Async("taskExecutor")
    public void onDevicePropertyUpEvent(DevicePropertyUpEvent devicePropertyUpEvent){
        log.info(String.valueOf(devicePropertyUpEvent));
        String payload = devicePropertyUpEvent.getPayload();
        DataFormat dataFormat = devicePropertyUpEvent.getDataFormat();
        JsonNode jsonNode = null;
        if (DataFormat.JSON.equals(dataFormat)){
             jsonNode = JsonUtil.parse(payload);
        }else {
            JsonUtil.parse(jsInvokeService.eval("",""));
        }

        ObjectNode objectNode = JsonUtil.createObjNode();
        objectNode.set("data",jsonNode);
        objectNode.set("metaData",devicePropertyUpEvent.getMetaData());

    }



    @EventListener
    @Async("taskExecutor")
    public void onDeviceMessageUpEvent(DeviceMessageUpEvent deviceMessageUpEvent){
        log.info(String.valueOf(deviceMessageUpEvent));
    }


    @Autowired
    public void setJsInvokeService(JsInvokeService jsInvokeService) {
        this.jsInvokeService = jsInvokeService;
    }
}
