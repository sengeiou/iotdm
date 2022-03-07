package com.aibaixun.iotdm.params;

import com.aibaixun.iotdm.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * http 转发目标配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@JsonTypeName(value = "MYSQL")
public class MySqlTargetConfig implements BaseTargetConfig{

    private Boolean batch;

    private Integer batchSize;

    private String sqlTemplate;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.MYSQL;
    }

    public Boolean getBatch() {
        return batch;
    }

    public void setBatch(Boolean batch) {
        this.batch = batch;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }

    public void setSqlTemplate(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }
}
