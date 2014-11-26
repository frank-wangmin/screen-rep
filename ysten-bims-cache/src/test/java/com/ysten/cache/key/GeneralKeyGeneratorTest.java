package com.ysten.cache.key;

import org.junit.Assert;
import org.junit.Test;

import com.ysten.cache.key.MethodClass.Person;

public class GeneralKeyGeneratorTest {
    
    private KeyGenerator keyGenerator = new GeneralKeyGenerator();
    
    @Test
    public void testKeyGenerator() throws SecurityException, NoSuchMethodException, Exception{
        Class<MethodClass> cls = (Class<MethodClass>) Class.forName("com.ysten.cache.key.MethodClass");
        
        Assert.assertEquals(keyGenerator.generatorKey(cls.getMethod("updatePersion", Person.class), "com:ysten:iptv:domain:person:name:#{person.name}",new Object[]{new Person("n","p","a")}),"com:ysten:iptv:domain:person:name:n");
        Assert.assertEquals(keyGenerator.generatorKey(cls.getMethod("updatePersion", Person.class), "com:ysten:iptv:domain:person:pwd:#{person.pwd}",new Object[]{new Person("n","p","a")}),"com:ysten:iptv:domain:person:pwd:p");
        Assert.assertEquals(keyGenerator.generatorKey(cls.getMethod("updatePersion", Person.class), "com:ysten:iptv:domain:person:age:#{person.age}",new Object[]{new Person("n","p","a")}),"com:ysten:iptv:domain:person:age:a");
        Assert.assertEquals(keyGenerator.generatorKey(cls.getMethod("clearData", Integer.class), "com:ysten:iptv:cache:integer:#{i}", new Object[]{1}),"com:ysten:iptv:cache:integer:1");
    }
}
