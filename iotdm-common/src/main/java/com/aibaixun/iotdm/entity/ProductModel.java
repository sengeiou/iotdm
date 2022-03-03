package com.aibaixun.iotdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 产品模型
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_product_model")
public class ProductModel extends BaseEntity {


    /**
     * 产品id
     */
    private String productId;

    /**
     * 模型名称 默认使用产品id 方便后期扩展成多个模型
     */
    @NotBlank(message = "模型名称不能为空")
    private String modelLabel;

    /**
     * 描述
     */
    @Length(max = 128,message = "模型描述不能超过128字符")
    private String description;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String modelLabel) {
        this.modelLabel = modelLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productId='" + productId + '\'' +
                ", modelLabel='" + modelLabel + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
