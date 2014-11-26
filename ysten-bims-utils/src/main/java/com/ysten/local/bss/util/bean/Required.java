package com.ysten.local.bss.util.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Neil on 2014-05-13.
 */
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {

    String message() default "";

    String[] group() default "all";

    boolean into() default false;
}
