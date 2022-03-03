package com.aibaixun.iotdm.support;

/**
 * kv 数据结构
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class KvData<T> {

    private String key;

    private T value;


    public KvData() {
    }

    public KvData(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
