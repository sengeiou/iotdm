package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 资源状态更改参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/18
 */
public class UpdateRuleResourceStatusParam {

    @NotBlank(message = "资源id不允许为空")
    private String resourceId;


    @NotNull(message = "资源状态不允许为空")
    private Boolean resourceStatus;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Boolean getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Boolean resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
}
