package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.entity.ModelCommand;
import com.aibaixun.iotdm.entity.ModelProperty;
import com.aibaixun.iotdm.entity.ProductModel;

import java.util.List;

/**
 * 产品模型详细信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class ProductModelInfo extends ProductModel {

    private List<ModelProperty> properties;

    private List<ModelCommand> commands;

    public List<ModelProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ModelProperty> properties) {
        this.properties = properties;
    }

    public List<ModelCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<ModelCommand> commands) {
        this.commands = commands;
    }
}
