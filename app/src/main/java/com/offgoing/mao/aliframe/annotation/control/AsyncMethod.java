package com.offgoing.mao.aliframe.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/12/28.
 */
//只有注释标记了@Retention(RetentionPolicy.RUNTIME) 的，我们才能通过反射来获得相关信息，
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AsyncMethod {
    public enum ArgType {normal,atom}
    public ArgType getMethodType() default ArgType.normal;
}
