package com.aibaixun.iotdm.business;

import java.io.Serializable;

/**
 * 业务数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public abstract class AbstractBusinessMsg implements Serializable {

    private MetaData metaData;


    public AbstractBusinessMsg() {
    }

    public AbstractBusinessMsg(MetaData metaData) {
        this.metaData = metaData;
    }


    public MetaData getMetaData() {
        return metaData;
    }


    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
