package com.aibaixun.iotdm.business;

import com.aibaixun.common.util.JsonUtil;
import com.aibaixun.iotdm.data.ProductEntityInfo;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.aibaixun.iotdm.entity.DevicePropertyReportEntity;
import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.msg.TsData;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 物模型匹配处理器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class MatchBusinessProcessor extends AbstractReportProcessor<PrePropertyBusinessMsg,MessageBusinessMsg> {


    private final String modelLabelKey = "modelId";

    private BusinessReportProcessor<PostPropertyBusinessMsg,MessageBusinessMsg> queueProcessor;



    @Override
    public void processProperty(PrePropertyBusinessMsg prePropertyBusinessMsg) {
        String productId = prePropertyBusinessMsg.getMetaData().getProductId();
        String deviceId = prePropertyBusinessMsg.getMetaData().getDeviceId();
        ProductEntityInfo productInfo = productService.queryProductInfoById(productId);
        List<ProductModelEntityInfo> models = productInfo.models;
        if (CollectionUtils.isEmpty(models)){
            doLog(deviceId, BusinessType.DEVICE2PLATFORM, BusinessStep.MATCH_MODEL,"product model is empty",false);
            return;
        }

        JsonNode prePropertyBusinessMsgPropertyJsonNode = prePropertyBusinessMsg.getPropertyJsonNode();
        if (Objects.isNull(prePropertyBusinessMsgPropertyJsonNode)){
            doLog(deviceId, BusinessType.DEVICE2PLATFORM, BusinessStep.MATCH_MODEL,"resolving data is empty",false);
            return;
        }

        List<ModelPropertyEntity> modelPropertyEntities = new ArrayList<>(models.size()*3);
        for (ProductModelEntityInfo model : models) {
            modelPropertyEntities.addAll(model.getProperties());
        }
        int size = prePropertyBusinessMsgPropertyJsonNode.size();
        List<DevicePropertyReportEntity> reportEntities = new ArrayList<>(size);
        try {
            if (prePropertyBusinessMsgPropertyJsonNode.isArray()){
                for (int i = 0; i < size; i++) {
                    JsonNode jsonNode = prePropertyBusinessMsgPropertyJsonNode.get(i);
                    reportEntities.addAll(doMatchPropertyAnd2DbEntity(deviceId,jsonNode,models,modelPropertyEntities));
                }
            }else {
                reportEntities.addAll(doMatchPropertyAnd2DbEntity(deviceId,prePropertyBusinessMsgPropertyJsonNode,models,modelPropertyEntities));
            }
        }catch (Exception e){
            doLog(deviceId, BusinessType.DEVICE2PLATFORM, BusinessStep.MATCH_MODEL,"Match Model property is empty"+e.getMessage(),false);
        }

        propertyReportService.saveOrUpdateBatch(reportEntities);
        doLog(deviceId, BusinessType.DEVICE2PLATFORM, BusinessStep.MATCH_MODEL, JsonUtil.toJSONString(reportEntities),true);
        queueProcessor.doProcessProperty(new PostPropertyBusinessMsg(prePropertyBusinessMsg.getMetaData(),toTsData(reportEntities)));
    }

    @Override
    public void processMessage(MessageBusinessMsg messageBusinessMsg) {
        queueProcessor.doProcessMessage(messageBusinessMsg);
    }


    private List<DevicePropertyReportEntity> doMatchPropertyAnd2DbEntity(String deviceId,JsonNode jsonNode,List<ProductModelEntityInfo> modelEntityInfos,List<ModelPropertyEntity> modelPropertyEntities){
        String modelLabel = null;
        if (jsonNode.has(modelLabelKey)){
            modelLabel = jsonNode.get(modelLabelKey).asText("");
        }
        int size = jsonNode.size();
        List<DevicePropertyReportEntity> results = new ArrayList<>(size);
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()){
            Map.Entry<String, JsonNode> next = fields.next();
            List<ModelPropertyEntity> matchProperty = matchProperty(modelLabel,next.getKey(), modelEntityInfos, modelPropertyEntities);
            changeDbEntity(deviceId, next.getValue(), matchProperty,results);
        }
        return results;
    }


    private List<ModelPropertyEntity> matchProperty(String modelLabel,String key,List<ProductModelEntityInfo> modelEntityInfos,List<ModelPropertyEntity> modelPropertyEntities){
        if (StringUtils.equals(modelLabelKey,key)) {
            return null;
        }
        if (StringUtils.isBlank(modelLabel)){
          return   modelPropertyEntities.stream().filter(e->StringUtils.equals(e.getPropertyLabel(),key)).collect(Collectors.toList());
        }
        ProductModelEntityInfo first = modelEntityInfos.stream().filter(e -> StringUtils.equals(e.getModelLabel(), modelLabel)).findAny().orElse(null);
        if (Objects.isNull(first)){
            return null;
        }
        return first.getProperties().stream().filter(e->StringUtils.equals(e.getPropertyLabel(),key)).collect(Collectors.toList());
    }


    public void changeDbEntity(String deviceId,JsonNode jsonNode,List<ModelPropertyEntity> matchProperties,List<DevicePropertyReportEntity> reportEntities){
        if (CollectionUtils.isEmpty(matchProperties)){
            return;
        }
        for (ModelPropertyEntity modelPropertyEntity : matchProperties) {
            String id = modelPropertyEntity.getId();
            String value = jsonNode.asText();
            String label = modelPropertyEntity.getPropertyLabel();
            reportEntities.add(new DevicePropertyReportEntity(deviceId,id,value,label));
        }
    }


    private List<TsData> toTsData (List<DevicePropertyReportEntity> devicePropertyReportEntities) {
        List<TsData> result = new ArrayList<>();
        for (DevicePropertyReportEntity entity : devicePropertyReportEntities) {
            result.add(new TsData(entity.getTs(), entity.getPropertyLabel(),entity.getPropertyValue()));
        }
        return result;
    }


    @Autowired
    public void setQueueProcessor(BusinessReportProcessor<PostPropertyBusinessMsg, MessageBusinessMsg> queueProcessor) {
        this.queueProcessor = queueProcessor;
    }
}
