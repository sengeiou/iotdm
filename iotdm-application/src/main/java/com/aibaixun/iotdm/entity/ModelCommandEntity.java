package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.support.BaseParam;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;


/**
 * <p>
 * 模型命令
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName(value = "t_model_command", autoResultMap = true)
public class ModelCommandEntity extends BaseEntity {

    /**
     * 产品模型id
     */
    private String productModelId;

    /**
     * 命令名称，单个模型不唯一
     */
    private String commandLabel;

    /**
     * 参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<BaseParam>  params;

    /**
     * 返回结果
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<BaseParam> responses;

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

    public String getCommandLabel() {
        return commandLabel;
    }

    public void setCommandLabel(String commandLabel) {
        this.commandLabel = commandLabel;
    }

    public List<BaseParam> getParams() {
        return params;
    }

    public void setParams(List<BaseParam> params) {
        this.params = params;
    }

    public List<BaseParam> getResponses() {
        return responses;
    }

    public void setResponses(List<BaseParam> responses) {
        this.responses = responses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ModelCommand{" +
                "productModelId='" + productModelId + '\'' +
                ", commandLabel='" + commandLabel + '\'' +
                ", params='" + params + '\'' +
                ", responses='" + responses + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
