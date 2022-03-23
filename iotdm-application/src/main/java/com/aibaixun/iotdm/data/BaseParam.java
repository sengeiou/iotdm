package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.enums.DataType;
import com.aibaixun.iotdm.enums.ParamScope;

import java.io.Serializable;

/**
 * 基础参数
 * <p>主要使用场景是命令下发</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class BaseParam implements Serializable {

    private String paramLabel;

    private DataType dataType;

    private Boolean required;

    private String unit;

    private String minValue;

    private String maxValue;

    private ParamScope scope;

    private String description;


    public String getParamLabel() {
        return paramLabel;
    }

    public void setParamLabel(String paramLabel) {
        this.paramLabel = paramLabel;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public ParamScope getScope() {
        return scope;
    }

    public void setScope(ParamScope scope) {
        this.scope = scope;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BaseParam{" +
                "paramLabel='" + paramLabel + '\'' +
                ", dataType=" + dataType +
                ", required=" + required +
                ", unit='" + unit + '\'' +
                ", minValue='" + minValue + '\'' +
                ", maxValue='" + maxValue + '\'' +
                ", scope=" + scope +
                '}';
    }


}
