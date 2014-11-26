package com.ysten.cache.mem;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.ysten.cache.Cache;

public class HashTableCache<V extends Serializable> implements Cache<String, V>{
    private Map<String, V> cache = new Hashtable<String, V>();
    
    @Override
    public V get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, V value) {
        cache.put(key, value);
    }

    @Override
    public void put(String key, V value, Long timeout, TimeUnit timeUnit) {
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void expire(String key, Long timeout, TimeUnit timeUnit) {
        cache.remove(key);
    }

    @Override
    public void clearAllPanel() {

    }

    @Override
    public void clearPanelInterface(){

    }

    @Override
    public void clearAnimation() {

    }

    @Override
    public void clearBackgroundImage() {

    }

    @Override
    public void clearBootsrap() {

    }

    @Override
    public Set<String> findKeysByRegular(String regular) {
        return null;
    }
}
