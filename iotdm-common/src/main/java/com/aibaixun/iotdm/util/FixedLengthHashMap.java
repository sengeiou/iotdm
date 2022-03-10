package com.aibaixun.iotdm.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 定长hashMap
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class FixedLengthHashMap<K,V> extends LinkedHashMap<K,V> {

    private final int fixedLength;

    public FixedLengthHashMap(int fixedLength) {
        super(fixedLength);
        this.fixedLength = fixedLength;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>=fixedLength;
    }
}
