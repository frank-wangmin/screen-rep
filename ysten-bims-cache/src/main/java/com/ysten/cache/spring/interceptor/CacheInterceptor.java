package com.ysten.cache.spring.interceptor;

import com.ysten.cache.Cache;
import com.ysten.cache.annotation.*;
import com.ysten.cache.enums.ClearRedisType;
import com.ysten.cache.jms.RedisMessageSender;
import com.ysten.cache.key.GeneralKeyGenerator;
import com.ysten.cache.key.KeyGenerator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.jms.MessageListener;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 获取拦截器<br/>
 * AOP实现，对方法进行缓存或缓存清理处理
 *
 * @author li.t
 */
public class CacheInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInterceptor.class);

    private KeyGenerator keyGenerator = new GeneralKeyGenerator();

    private Cache<String, Serializable> cache;

//    @Autowired
//    private RedisMessageSender redisMessageSender;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] params = invocation.getArguments();
        try {
//            LOGGER.info("########################method####################### " + method.getName());
            MethodCache methodCache = method.getAnnotation(MethodCache.class);
            if (methodCache != null) {
                return cacheObject(invocation, method, params, methodCache);
            } else {
                MethodFlush methodFlush = method.getAnnotation(MethodFlush.class);
                if (methodFlush != null) {
                    clearObject(method, params, methodFlush);
                }
                MethodFlushInterfacePackage methodFlushInterfacePackage = method.getAnnotation(MethodFlushInterfacePackage.class);
                if (methodFlushInterfacePackage != null) {
                    LOGGER.debug("*************************clear panel interface****************************");
                    cache.clearPanelInterface();
//                    redisMessageSender.sendMessage(ClearRedisType.PANEL.toString());
                }
                MethodFlushAllPanel methodFlushAllPanel = method.getAnnotation(MethodFlushAllPanel.class);
                if (methodFlushAllPanel != null) {
                    LOGGER.debug("*************************clear all panel****************************");
                    this.cache.clearAllPanel();
//                    redisMessageSender.sendMessage(ClearRedisType.ALLPANEL.toString());
                }
                MethodFlushAnimation methodFlushAnimation = method.getAnnotation(MethodFlushAnimation.class);
                if (methodFlushAnimation != null) {
                    LOGGER.debug("*************************clear animation interface****************************");
                    cache.clearAnimation();
//                    redisMessageSender.sendMessage(ClearRedisType.ANIMATION.toString());
                }
                MethodFlushBackgroundImage methodFlushBackgroundImage = method.getAnnotation(MethodFlushBackgroundImage.class);
                if (methodFlushBackgroundImage != null) {
                    LOGGER.debug("*************************clear backgroundImage interface****************************");
                    cache.clearBackgroundImage();
//                    redisMessageSender.sendMessage(ClearRedisType.BACKGROUND.toString());
                }
                MethodFlushBootsrap methodFlushBootsrap = method.getAnnotation(MethodFlushBootsrap.class);
                if (methodFlushBootsrap != null) {
                    LOGGER.debug("*************************clear bootsrap interface****************************");
                    cache.clearBootsrap();
//                    redisMessageSender.sendMessage(ClearRedisType.BOOTSTRAP.toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("cache invoke error : {}", e);
		    return invocation.proceed();
        }
        return invocation.proceed();
    }

    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public Cache<String, Serializable> getCache() {
        return cache;
    }

    public void setCache(Cache<String, Serializable> cache) {
        this.cache = cache;
    }

    /**
     * clean from cache
     *
     * @param method
     * @param params
     * @param methodFlush
     * @throws Exception
     */
    private void clearObject(Method method, Object[] params,
                             MethodFlush methodFlush) throws Exception {
        for (String key : methodFlush.keys()) {
            try {
                String k = this.keyGenerator.generatorKey(method, key, params);
                LOGGER.debug("remove from cache , key = {}", k);
                this.cache.remove(k);
            } catch (Exception e) {
                LOGGER.error("clear Object error : {}" + e);
            }
        }
    }

    /**
     * cache object
     *
     * @param invocation
     * @param method
     * @param params
     * @param methodCache
     * @return
     * @throws Throwable
     */
    private Object cacheObject(MethodInvocation invocation, Method method, Object[] params,
                               MethodCache methodCache) throws Throwable {

        try {
            String key = keyGenerator.generatorKey(method, methodCache.key(), params);
            if (!StringUtils.isEmpty(key.trim())) {
                Object result = cache.get(key);
                if (result == null) {
                    result = invocation.proceed();
                    if (result != null && result instanceof Serializable) {
                        if (methodCache.useTimeOut()) {
                            cache.put(key, (Serializable) result, methodCache.timeOut(), methodCache.timeUnit());
                            LOGGER.debug("cache object key={},object={},timeout={},timeUnit={}", new Object[]{key, result, methodCache.timeOut(), methodCache.timeUnit()});
                        } else {
                            cache.put(key, (Serializable) result);
                            LOGGER.debug("cache object key={},object={}", key, result);
                        }
                    }
                } else if (methodCache.resetTimeOut()) {
                    cache.expire(key, methodCache.timeOut(), methodCache.timeUnit());
                    LOGGER.debug("reset timeout key={},timeout={},timeUnit={}", new Object[]{key, methodCache.timeOut(), methodCache.timeUnit()});
                }
                return result;
            }
        } catch (Exception e) {
//            LOGGER.error("cache object error : {}" + e);
            return invocation.proceed();
        }
        return invocation.proceed();
    }
}
