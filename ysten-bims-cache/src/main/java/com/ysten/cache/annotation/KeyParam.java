package com.ysten.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存方法中参数别名注解
 * @author li.t
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyParam {
    
	/**
	 * 绑定Key值
	 * @return
	 */
	String value();
}
