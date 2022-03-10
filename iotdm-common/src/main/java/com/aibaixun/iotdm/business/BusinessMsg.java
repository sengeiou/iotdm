package com.aibaixun.iotdm.business;

/**
 * 业务流转消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface BusinessMsg {

    /**
     * 获取元数据
     * @return 元数据
     */
    MetaData getMetaData();

    /**
     * 设置元数据
     */
    void  setMetaData(MetaData metaData);
}
