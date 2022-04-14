package com.aibaixun.iotdm.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * 设备命令下发参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
@JsonIgnoreProperties({"modelId", "toDeviceType"})
public class ToDeviceCommandTransportData extends ToDeviceBaseData{

    private String commandLabel;

    private String modelId;

    private Map<String,Object> params;

    public ToDeviceCommandTransportData(ToDeviceType toDeviceType) {
        super(toDeviceType);
    }

    public String getCommandLabel() {
        return commandLabel;
    }

    public void setCommandLabel(String commandLabel) {
        this.commandLabel = commandLabel;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


}
