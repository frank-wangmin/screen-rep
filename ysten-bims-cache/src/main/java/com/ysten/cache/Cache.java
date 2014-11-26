package com.ysten.cache;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 统一Cache接口，整合第三方的Cache实现<br/>
 * @author li.t
 *
 * @param <K>
 *          缓存Key类型
 * @param <T>
 *          缓存Value类型
 */
public interface Cache<K extends Serializable,T extends Serializable> {
	
    /**
     * get data of key
     * @param key
     *          cacheKey
     * @return
     */
	public T get(K key);
	
	/**
	 * set value of key
	 * @param key
	 *         cacheKey
	 * @param value
	 *         cacheValue
	 */
	public void put(K key,T value);
	
	/**
	 * set value of key with expire infor
	 * @param key
	 *         cacheKey
	 * @param value
	 *         cacheValue
	 * @param timeout
	 *         timeOut
	 * @param timeUnit
	 *         timeUnit
	 */
	public void put(K key,T value,Long timeout,TimeUnit timeUnit);
	
	/**
	 * remove key
	 * @param key
	 *         cacheKey
	 */
	public void remove(K key);
	
	/**
	 * flush cache
	 */
	public void clear();
	
	/**
	 * the size of object in cache
	 * @return
	 */
	public long size();
	
	/**
	 * update cache key's expire infor
	 * @param key
	 * @param timeout
	 * @param timeUnit
	 */
	public void expire(K key, Long timeout,TimeUnit timeUnit);

    /**
     * clear all panel
     * @author frank
     */
    public void clearAllPanel();

    /**
     * clear panel interface
     * @author frank
     */
    public void clearPanelInterface();

    /**
     * clear animation interface
     * @author frank
     */
    public void clearAnimation();

    /**
     * clear backgroundImage interface
     * @author frank
     */
    public void clearBackgroundImage();

    public void clearBootsrap();

    public Set<String> findKeysByRegular(String regular);
}
