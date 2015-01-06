package com.offgoing.mao.aliframe.proxy;

import android.content.Context;
import android.os.Handler;

import com.offgoing.mao.aliframe.proxy.inter.Interceptor;

import java.io.File;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ControlFactory<T> {
    /**
     * 使用Dexmaker时需要给实例设置一个cacheFile路径
     */
    private static File mCacheFile;
    public static void init(Context context){
        mCacheFile = context.getDir("dx",Context.MODE_PRIVATE);
    }
    public static <T>T getControlInstance(Class<T>clazz,Handler handler){
        Class<?>constructorArgTypes[]= new Class<?>[]{Handler.class};
        Object[]constructorArgValues = new Object[]{handler};
        Enhancer<T>enhancer = new Enhancer<T>(clazz,mCacheFile,constructorArgTypes,constructorArgValues);
        enhancer.setInterceptors(new Interceptor[]{new AsyncMethodInterceptor(handler)});
        return enhancer.create();
    }
}
