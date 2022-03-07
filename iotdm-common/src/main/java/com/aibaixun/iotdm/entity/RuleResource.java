package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.ResourceType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;


/**
 * <p>
 * 规则资源
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_rule_resource")
public class RuleResource extends BaseEntity {

    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    private ResourceType resourceType;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源参数
     */
    @NotNull(message = "资源参数不能为空")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JsonNode configuration;

    /**
     * 是否删除
     */
    private Boolean deleted;


    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getConfiguration() {
        return configuration;
    }

    public void setConfiguration(JsonNode configuration) {
        this.configuration = configuration;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
