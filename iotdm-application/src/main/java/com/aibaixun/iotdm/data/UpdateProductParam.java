package com.aibaixun.iotdm.data;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 产品修改参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public class UpdateProductParam {


    @NotBlank(message = "产品id不能为空")
    private String id;

    @NotBlank(message = "产品名称不能为空")
    private String productLabel;


    @Length(max = 128,message = "产品描述不能超过128字符")
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
