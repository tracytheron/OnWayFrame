package com.offgoing.mao.aliframe.proxy.inter;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/12/28.
 */
public interface Interceptor {
    /**
     * 动态代理
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object interceptor(final Object proxy,
                              Method method,
                              final Object[] args) throws Throwable;
}
