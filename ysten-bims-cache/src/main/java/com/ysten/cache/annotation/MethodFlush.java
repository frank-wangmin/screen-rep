package com.ysten.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存清理方法注解<br/>
 * 当注解的方法被调用时，自动清理由keys指定的缓存。
 * @author li.t
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodFlush {
    
    /**
     * 清理缓存的keys<br/>
     * 可以应用#{beanName.propertyName}表达式<br/>
     * 其中beanName为由KeyParam注解所指定的参数别名.
     * @return
     */
	String[] keys();
}
