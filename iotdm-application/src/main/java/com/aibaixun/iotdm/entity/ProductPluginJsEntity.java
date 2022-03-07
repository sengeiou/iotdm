package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.Deployment;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 产品插件-js插件
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_product_plugin_js")
public class ProductPluginJsEntity extends BaseEntity {

    /**
     * 产品id
     */
    @NotBlank(message = "产品不能为空")
    private String productId;

    /**
     * js脚本
     */
    @NotBlank(message = "js脚本内容不允许为空")
    private String jsScriptBody;

    /**
     * 发布类型
     */
    @NotNull(message = "发布类型不允许为空")
    private Deployment deployment;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getJsScriptBody() {
        return jsScriptBody;
    }

    public void setJsScriptBody(String jsScriptBody) {
        this.jsScriptBody = jsScriptBody;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    @Override
    public String toString() {
        return "ProductPluginJs{" +
                "productId='" + productId + '\'' +
                ", jsScriptBody='" + jsScriptBody + '\'' +
                ", deployment=" + deployment +
                '}';
    }
}
