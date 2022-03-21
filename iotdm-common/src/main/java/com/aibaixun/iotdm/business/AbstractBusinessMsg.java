package com.aibaixun.iotdm.business;

/**
 * 业务数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public abstract class AbstractBusinessMsg {

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
