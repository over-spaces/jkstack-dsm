package com.jkstack.dsm.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface IRedisCommand<K, V> {

    // =================================================================================================================
    // String操作

    void set(K key, V value);

    void set(K key, V value, long expireTime, TimeUnit unit);

    V get(K key);

    boolean delete(K key);

    boolean expire(K key, long expireTime, TimeUnit unit);

    boolean exists(K key);

    // =================================================================================================================
    // Hash操作

    void put(K key, K field, V value);

    void put(K key, Map<K, V> map);

    V getValueFromHash(K key, K field);

    Map<Object, Object> getMapFromHash(K key);

    void deleteHashField(K key, K field);

    Set<Object> getHashKeys(K key);

    List<V> getHashValues(String key);
}
