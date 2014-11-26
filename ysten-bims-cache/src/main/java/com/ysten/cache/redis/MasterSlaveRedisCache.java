package com.ysten.cache.redis;

import com.ysten.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 主从redis cache实现
 * Created by frank on 14-7-25.
 */
public class MasterSlaveRedisCache<V extends Serializable> implements Cache<String, V> {

    public static final String PANEL_INTERFACE_KEY = "panel:interface:*";

    public static final String PANEL_COMMON_KEY = "ysten:local:bss:panel*";

    public static final String ANIMATION_INTERFACE = "animation:interface:ystenId:*";

    public static final String BACKGROUND_IMAGE_INTERFACE = "backgroundImage:interface:ystenId:*";

    public static final String BOOTSRAP_INTERFACE = "bootsrap:interface:ystenId:*";

    private RedisTemplate<String, V> redisTemplate;

    private RedisTemplate<String,V> slaveRedisTemplate;

    public MasterSlaveRedisCache() {
        super();
    }

    public MasterSlaveRedisCache(RedisTemplate<String, V> redisTemplate,RedisTemplate<String,V> slaveRedisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
        this.slaveRedisTemplate = slaveRedisTemplate;
    }

    public void destroy() {
    }

    @Override
    public V get(String key) {
        return slaveRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void put(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(String key, V value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {
    }

    @Override
    public long size() {
        return -1;
    }

    @Override
    public void expire(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public RedisTemplate<String, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void clearPanelInterface() {
        Set<String> keys = redisTemplate.keys(PANEL_COMMON_KEY);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void clearAnimation() {
        Set<String> keys = redisTemplate.keys(ANIMATION_INTERFACE);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void clearBackgroundImage() {
        Set<String> keys = redisTemplate.keys(BACKGROUND_IMAGE_INTERFACE);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void clearBootsrap() {
        Set<String> keys = redisTemplate.keys(BOOTSRAP_INTERFACE);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void clearAllPanel() {
        Set<String> keys = redisTemplate.keys(PANEL_INTERFACE_KEY);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public Set<String> findKeysByRegular(String regular) {
        return null;
    }
}
