package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.entity.ProductModelEntity;

import java.util.List;

/**
 * 产品模型详细信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class ProductModelEntityInfo extends ProductModelEntity {

    private List<ModelPropertyEntity> properties;

    private List<ModelCommandEntity> commands;

    public List<ModelPropertyEntity> getProperties() {
        return properties;
    }

    public void setProperties(List<ModelPropertyEntity> properties) {
        this.properties = properties;
    }

    public List<ModelCommandEntity> getCommands() {
        return commands;
    }

    public void setCommands(List<ModelCommandEntity> commands) {
        this.commands = commands;
    }
}
