package com.ysten.cache.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * cache的redis实现
 * @author li.t
 *
 * @param <V>
 *          缓存值类型
 */
public class StringRedisCache extends RedisCache<String> {
	public StringRedisCache() {
		super();
	}

	public StringRedisCache(StringRedisTemplate redisTemplate) {
		super(redisTemplate);
	}
}
