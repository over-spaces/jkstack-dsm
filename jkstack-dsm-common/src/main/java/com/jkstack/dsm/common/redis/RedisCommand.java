package com.jkstack.dsm.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCommand implements IRedisCommand<String, Object> {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public void set(String key, Object value, long expireTime, TimeUnit unit) {
        redisTemplate.boundValueOps(key).set(value, expireTime, unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public boolean delete(String key) {
        if(exists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    @Override
    public boolean expire(String key, long expireTime, TimeUnit unit) {
        return redisTemplate.expire(key, expireTime, unit);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
    // ================================================================================================================

    @Override
    public void put(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    @Override
    public void put(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    @Override
    public Set<Object> getHashKeys(String key){
        return redisTemplate.boundHashOps(key).keys();
    }

    @Override
    public List<Object> getHashValues(String key){
        return redisTemplate.boundHashOps(key).values();
    }

    @Override
    public Object getValueFromHash(String key, String field) {
        return redisTemplate.boundHashOps(key).get(field);
    }

    @Override
    public Map<Object, Object> getMapFromHash(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    @Override
    public void deleteHashField(String key, String field) {
         redisTemplate.boundHashOps(key).delete(field);
    }
}
