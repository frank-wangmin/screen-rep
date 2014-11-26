package com.ysten.cache.key;

import java.lang.reflect.Method;

/**
 * 缓存的Key生成器
 * @author li.t
 *
 */
public interface KeyGenerator {
    /**
     * generate cache key 
     * @param method
     *          method
     * @param key
     *          key value
     * @param params
     *          the params of method 
     * @return
     *          cache key
     * @throws Exception
     */
	public String generatorKey(Method method,String key,Object[] params) throws Exception;
}
