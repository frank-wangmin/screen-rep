package com.ysten.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存方法注解<br/>
 * 标注特定方法应用缓存机制<br/>
 * 在方法被调用时，首先检查缓存中是不存在对应的值，如果有则直接返回缓存中的结果；<br/>
 * 如果没有则将调用实际方法获取数据，并将数据放至缓存中.
 * @author li.t
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodCache {
	
	/**
	 * 缓存key值<br/>
	 * 可以应用#{beanName.propertyName}表达式<br/>
	 * 其中beanName为由KeyParam注解所指定的参数别名.
	 * @return
	 */
	String key();
	
	/**
	 * 获取超时时间<br/>
	 * 默认为15
	 * @return
	 */
	long timeOut() default 15l;
	
	/**
	 * 超时的时间单位<br/>
	 * 默认为MINUTES
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.MINUTES;
	
	/**
	 * 是否应有超时机制<br/>
	 * 默认为false
	 * @return
	 */
	boolean useTimeOut() default false;
	
	/**
	 * 是否在访问的时候重置超时时间<br/>
	 * 默认为false
	 * @return
	 */
	boolean resetTimeOut() default false;
}
