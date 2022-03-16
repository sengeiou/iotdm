package com.aibaixun.iotdm.server;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.toolkit.HexTool;
import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.data.BaseParam;
import com.aibaixun.iotdm.entity.DeviceCommandSendEntity;
import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.CommandSendStatus;
import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.event.ToDeviceConfigEvent;
import com.aibaixun.iotdm.event.ToDeviceControlEvent;
import com.aibaixun.iotdm.script.JsInvokeService;
import com.aibaixun.iotdm.service.IDeviceCommandSendService;
import com.aibaixun.iotdm.service.IModelCommandService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.service.IotDmEventPublisher;
import com.aibaixun.iotdm.support.ToDeviceCommandTransportData;
import com.aibaixun.iotdm.support.ToDeviceConfigTransportData;
import com.aibaixun.iotdm.support.ToDeviceType;
import com.aibaixun.iotdm.transport.SessionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 发送到设备 的消息处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@Service
public class Default2DeviceProcessor implements ToDeviceProcessor{


    private IDeviceCommandSendService deviceCommandSendService;

    private IModelCommandService modelCommandService;

    private IProductService productService;

    private JsInvokeService jsInvokeService;

    private DeviceLogProcessor deviceLogProcessor;

    private IotDmEventPublisher eventPublisher;


    @Override
    public void processConfig(String deviceId, String productId, String host, Integer port) throws BaseException {

        ProductEntity product = productService.getById(productId);
        if (Objects.isNull(product)){
            throw new BaseException("产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        ToDeviceConfigTransportData deviceConfigTransportData = new ToDeviceConfigTransportData(ToDeviceType.CONFIG,host,port);
        String payload = JsonUtil.toJSONString(deviceConfigTransportData);
        if (DataFormat.BINARY.equals(product.getDataFormat())){
            try {
                Object o = jsInvokeService.invokeEncodeFunction(productId, deviceConfigTransportData);
                payload = HexTool.encodeHexStr((byte[]) o);
            }catch (Exception e){
                doLog(deviceId,BusinessStep.PLATFORM_RESOLVING_DATA_ERROR,e.getMessage(),false);
            }
        }
        ToDeviceConfigEvent toDeviceConfigEvent = new ToDeviceConfigEvent(new SessionId(deviceId, productId), payload);
        eventPublisher.publish2DeviceConfigReqEvent(toDeviceConfigEvent);
    }

    @Override
    public void processOta(String deviceId, String productId, String otaId) {

    }

    @Override
    public void processControl(String deviceId, String productId, String commandId, Map<String, Object> params) throws BaseException {
        ProductEntity product = productService.getById(productId);
        if (Objects.isNull(product)){
            throw new BaseException("产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        ModelCommandEntity modelCommand = modelCommandService.getById(commandId);
        if (Objects.isNull(modelCommand)){
            throw new BaseException("发送命令不存在", BaseResultCode.GENERAL_ERROR);
        }
        List<BaseParam> modelCommandParams = modelCommand.getParams();
        Map<String,Object> toDeviceParam = new HashMap<>(modelCommandParams.size());
        for (BaseParam baseParam: modelCommandParams){
            String paramLabel = baseParam.getParamLabel();
            Object o = params.get(paramLabel);
            if (Objects.nonNull(o)){
                toDeviceParam.put(paramLabel,o);
            }
        }
        ToDeviceCommandTransportData toDeviceCommandData = new ToDeviceCommandTransportData(ToDeviceType.COMMAND);
        toDeviceCommandData.setCommandLabel(modelCommand.getCommandLabel());
        toDeviceCommandData.setModelId(modelCommand.getProductModelId());
        toDeviceCommandData.setParams(toDeviceParam);
        String payload = null;
        doLog(deviceId,BusinessStep.PLATFORM_RESOLVING_DATA,JsonUtil.toJSONString(toDeviceCommandData),true);
        if (DataFormat.BINARY.equals(product.getDataFormat())){
            try {
                Object o = jsInvokeService.invokeEncodeFunction(productId, toDeviceCommandData);
                payload = HexTool.encodeHexStr((byte[]) o);
            }catch (Exception e){
                doLog(deviceId,BusinessStep.PLATFORM_RESOLVING_DATA_ERROR,e.getMessage(),false);
            }
        }else {
            payload = JsonUtil.toJSONString(toDeviceCommandData);
        }
        doLog(deviceId,BusinessStep.PLATFORM_RESOLVING_DATA_SUCCESS,JsonUtil.toJSONString(toDeviceCommandData),true);
        DeviceCommandSendEntity deviceCommandSendEntity = new DeviceCommandSendEntity();
        deviceCommandSendEntity.setDeviceId(deviceId);
        deviceCommandSendEntity.setCommandId(commandId);
        deviceCommandSendEntity.setCommandLabel(modelCommand.getCommandLabel());
        deviceCommandSendEntity.setParams(payload);
        deviceCommandSendEntity.setTs(Instant.now().toEpochMilli());
        deviceCommandSendEntity.setReqId(ThreadLocalRandom.current().nextInt(2<< 10));
        deviceCommandSendEntity.setSendStatus(CommandSendStatus.SEND);
        deviceCommandSendService.save(deviceCommandSendEntity);
        ToDeviceControlEvent toDeviceControlEvent = new ToDeviceControlEvent(new SessionId(deviceId, productId), payload);
        toDeviceControlEvent.setSendId(deviceCommandSendEntity.getId());
        eventPublisher.publish2ControlReqEvent(toDeviceControlEvent);
    }

    private void  doLog (String deviceId, BusinessStep businessStep, String businessDetails, Boolean messageStatus){
        deviceLogProcessor.doPlatform2DeviceLLog(deviceId,businessStep,businessDetails,messageStatus);
    }

    @Autowired
    public IDeviceCommandSendService getDeviceCommandSendService() {
        return deviceCommandSendService;
    }

    @Autowired
    public void setDeviceCommandSendService(IDeviceCommandSendService deviceCommandSendService) {
        this.deviceCommandSendService = deviceCommandSendService;
    }

    @Autowired
    public IModelCommandService getModelCommandService() {
        return modelCommandService;
    }

    @Autowired
    public void setModelCommandService(IModelCommandService modelCommandService) {
        this.modelCommandService = modelCommandService;
    }

    @Autowired
    public IProductService getProductService() {
        return productService;
    }

    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setJsInvokeService(JsInvokeService jsInvokeService) {
        this.jsInvokeService = jsInvokeService;
    }


    @Autowired
    public void setDeviceLogProcessor(DeviceLogProcessor deviceLogProcessor) {
        this.deviceLogProcessor = deviceLogProcessor;
    }

    @Autowired
    public void setEventPublisher(IotDmEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
