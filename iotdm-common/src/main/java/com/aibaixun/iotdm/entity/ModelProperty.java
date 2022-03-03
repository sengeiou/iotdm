package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.DataType;
import com.aibaixun.iotdm.enums.ParamScope;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * <p>
 * 模型属性
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_model_property")
public class ModelProperty extends BaseEntity {


    /**
     * 产品模型id
     */
    private String productModelId;

    /**
     * 属性名称,在单个模型内唯一
     */
    private String propertyLabel;

    /**
     * 数据类型
     */
    private DataType dataType;

    /**
     * 访问权限
     */
    private ParamScope scope;

    /**
     * 最大
     */
    private BigDecimal maxValue;

    /**
     * 最小值
     */
    private BigDecimal minValue;

    /**
     * 属性长度
     */
    private Integer propertyLength;

    /**
     * 描述
     */
    private String description;


    public String getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(String productModelId) {
        this.productModelId = productModelId;
    }

    public String getPropertyLabel() {
        return propertyLabel;
    }

    public void setPropertyLabel(String propertyLabel) {
        this.propertyLabel = propertyLabel;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ParamScope getScope() {
        return scope;
    }

    public void setScope(ParamScope scope) {
        this.scope = scope;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public Integer getPropertyLength() {
        return propertyLength;
    }

    public void setPropertyLength(Integer propertyLength) {
        this.propertyLength = propertyLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ModelProperty{" +
                "productModelId='" + productModelId + '\'' +
                ", propertyLabel='" + propertyLabel + '\'' +
                ", dataType=" + dataType +
                ", scope=" + scope +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", propertyLength=" + propertyLength +
                ", description='" + description + '\'' +
                '}';
    }
}
