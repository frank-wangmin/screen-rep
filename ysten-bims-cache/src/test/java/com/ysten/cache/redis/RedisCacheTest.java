package com.ysten.cache.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ysten.cache.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/cache.xml")
public class RedisCacheTest {
	private String key = "com:ysten:iptv:cache:test";
	
	private Person value = new Person();
	
    @Autowired
    private RedisTemplate<String, Person> redisTemplate;
    
	private Cache<String, Person> cache;
	
	@Before
	public void init(){
        RedisCache<Person> cache = new RedisCache<Person>();
		cache.setRedisTemplate(redisTemplate);
		this.cache = cache;
	}
	
	@Test
	public void testGetAndSet(){
		Assert.assertNull(this.cache.get(key));
		this.cache.put(key, value);
		Assert.assertNotNull(this.cache.get(key));
		
		this.cache.put(key, value,5l,TimeUnit.MINUTES);
		Assert.assertNotNull(this.cache.get(key));
	}
	@Test
	public void testRemove(){
		Assert.assertNull(this.cache.get(key));
		this.cache.put(key, value);
		Assert.assertNotNull(this.cache.get(key));
		this.cache.remove(key);
		Assert.assertNull(this.cache.get(key));
	}
	
	@After
	public void clean(){
		this.cache.remove(key);
	}
	
	public static class Person implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2095258928263164945L;
	}
}
