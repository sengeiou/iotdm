package com.aibaixun.iotdm.data;

/**
 * kv 数据结构
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class KvData<T> {

    private String label;

    private T dataValue;


    public KvData() {
    }

    public KvData(String label, T dataValue) {
        this.label = label;
        this.dataValue = dataValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getDataValue() {
        return dataValue;
    }

    public void setDataValue(T dataValue) {
        this.dataValue = dataValue;
    }
}
