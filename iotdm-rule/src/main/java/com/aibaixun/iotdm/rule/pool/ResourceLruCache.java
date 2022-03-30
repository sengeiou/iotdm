package com.aibaixun.iotdm.rule.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 资源LRU 缓存
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public class ResourceLruCache<V> {

    private final Logger log = LoggerFactory.getLogger(ResourceLruCache.class);

    private final LinkedHashMap<String, V> cache;

    public ResourceLruCache(final int maxSize) {
        this.cache = new LinkedHashMap<String, V>(16, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                return this.size() > maxSize;
            }
        };
    }

    public V get(String key) {
        return this.cache.get(key);
    }

    public void put(String key, V value) {
        this.cache.put(key, value);
    }

    public boolean remove(String key) {
        log.warn("remove key:{}",key);
        return this.cache.remove(key) != null;
    }

    public long size() {
        return this.cache.size();
    }
}
