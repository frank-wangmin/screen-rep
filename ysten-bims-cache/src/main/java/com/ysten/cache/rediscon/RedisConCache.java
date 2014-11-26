package com.ysten.cache.rediscon;

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

import com.ysten.cache.IRedisConCache;


public class RedisConCache implements IRedisConCache {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	public boolean delKeysByPattern(String pattern) {
		try {
			Set<String> keys = this.keys(pattern);
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String keyitem = it.next();
				redisTemplate.delete(keyitem);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Set<String> keys(String pattern) {
		final byte[] rawKey = rawKey(pattern);

		Set<byte[]> rawKeys = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.keys(rawKey);
			}
		}, true);

		Set<String> keyStringset = new HashSet<String>();
		Iterator<byte[]> it = rawKeys.iterator();
		while (it.hasNext()) {
			byte[] keyitem = it.next();
			keyStringset.add(redisTemplate.getStringSerializer().deserialize(keyitem));
		}
		return keyStringset;
	}

	public byte[] rawKey(String key) {
		Assert.notNull(key, "non null key required");
		return redisTemplate.getStringSerializer().serialize(key);
	}

	public Object getRedis(final String sKey) {
		try {
			return redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] key = redisTemplate.getStringSerializer().serialize(sKey);
					if (connection.exists(key)) {
						byte[] value = connection.get(key);
						Object sValue = redisTemplate.getValueSerializer().deserialize(value);
						return sValue;
					}
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
