package com.ysten.cache;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * ListCache
 * 
 */
public interface IRedisConCache {

	boolean delKeysByPattern(String pattern);

	Set<String> keys(String pattern);

	byte[] rawKey(String key);

	Object getRedis(final String sKey);

}
