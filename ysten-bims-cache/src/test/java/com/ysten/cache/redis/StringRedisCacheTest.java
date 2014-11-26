package com.ysten.cache.redis;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ysten.cache.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/cache.xml")
public class StringRedisCacheTest {
    
    private String key = "com:ysten:iptv:cache:teststring";
    
    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    private Cache<String, String> cache;
    
    @Before
    public void init(){
        StringRedisCache cache = new StringRedisCache();
        cache.setRedisTemplate(redisTemplate);
        this.cache = cache;
    }

    @Test
    public void testGetAndSet(){
        Assert.assertNull(this.cache.get(key));
        this.cache.put(key, "test");
        Assert.assertEquals("test", this.cache.get(key));
        
        this.cache.put(key, "test", 5l,TimeUnit.MINUTES);
        Assert.assertEquals("test", this.cache.get(key));
        
        this.cache.remove(key);
        Assert.assertNull(this.cache.get(key));
    }

    @After
    public void clean(){
        this.cache.remove(key);
    }
}
