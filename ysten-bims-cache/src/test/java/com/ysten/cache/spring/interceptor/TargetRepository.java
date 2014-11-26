package com.ysten.cache.spring.interceptor;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;

public class TargetRepository {
	
	@MethodCache(key="com:ysten:iptv:#{i}:id")
	public int getInteger(@KeyParam("i")int i){
		//System.out.println(i);
		return i;
	}
	
	@MethodFlush(keys="com:ysten:iptv:#{i}:id")
	public void clearInteger(@KeyParam("i")int i){
	    
	}
}
