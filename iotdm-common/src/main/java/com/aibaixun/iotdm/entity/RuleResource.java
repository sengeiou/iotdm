package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.ResourceType;
import com.baomidou.mybatisplus.annotation.TableName;


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
    private ResourceType resourceType;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源参数
     */
    private String options;

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "RuleResource{" +
                "resourceType='" + resourceType + '\'' +
                ", description='" + description + '\'' +
                ", options='" + options + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
