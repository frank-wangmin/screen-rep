package com.ysten.cache.spring.interceptor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="classpath:/spring/*.xml")
public class CacheInterceptorTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private TargetRepository targetRepository;
	
	@Test
	public void test(){
		Assert.assertNotSame(targetRepository.getClass(), TargetRepository.class);

        targetRepository.getInteger(22);
	}
}
