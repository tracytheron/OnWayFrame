package com.offgoing.mao.aliframe.proxy;

import com.google.dexmaker.stock.ProxyBuilder;
import com.offgoing.mao.aliframe.proxy.inter.Interceptor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Enhancer<T> implements InvocationHandler {
    private Class<T>superClass;
    private File mCacheFile;
    private Class<?>[]constructorArgTypes;
    private Object[]constructorArgValues;
    /**
     * 代理拦截
     */
    private Interceptor[]interceptors;
    public Enhancer(Class<T>superClass,File mCacheFile,Class<?>[]constructorArgTypes,Object[]constructorArgValues){
        this.superClass = superClass;
        this.mCacheFile = mCacheFile;
        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgValues = constructorArgValues;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //防止这两个方法被放在子线程中调用
        if(method.getName().equalsIgnoreCase("hashCode")||method.getName().equalsIgnoreCase("toString")){
            return ProxyBuilder.callSuper(proxy,method,args);
        }
        return interceptors[0].interceptor(proxy,method,args);
    }
    public T create(){
        ProxyBuilder<T>proxyBuilder = ProxyBuilder.forClass(superClass);
        //这里要去调用baseControl的构造方法，借助构造方法把Handler传递到BaseControl
        if(constructorArgTypes!=null && constructorArgValues!=null){
            proxyBuilder.constructorArgTypes(constructorArgTypes);
            proxyBuilder.constructorArgValues(constructorArgValues);
        }
        proxyBuilder.dexCache(mCacheFile);
        proxyBuilder.handler(this);
        try {
            return proxyBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setInterceptors(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }
}
