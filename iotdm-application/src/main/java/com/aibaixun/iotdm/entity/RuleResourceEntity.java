package com.aibaixun.iotdm.entity;


import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 规则资源
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName(value = "t_rule_resource",autoResultMap = true)
public class RuleResourceEntity extends BaseEntity {


    /**
     * 资源名称
     */
    private String resourceLabel;

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 资源参数
     */
    @NotNull(message = "资源参数不能为空")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private BaseResourceConfig configuration;

    /**
     * 是否删除
     */
    private Boolean deleted;


    public String getResourceLabel() {
        return resourceLabel;
    }

    public void setResourceLabel(String resourceLabel) {
        this.resourceLabel = resourceLabel;
    }



    public BaseResourceConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(BaseResourceConfig configuration) {
        this.configuration = configuration;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }


    @Override
    public String toString() {
        return "RuleResourceEntity{" +
                "resourceLabel='" + resourceLabel + '\'' +
                ", resourceType=" + resourceType +
                ", configuration=" + configuration +
                ", deleted=" + deleted +
                '}';
    }
}
