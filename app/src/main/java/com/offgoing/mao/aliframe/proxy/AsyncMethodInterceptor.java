package com.offgoing.mao.aliframe.proxy;



import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.google.dexmaker.stock.ProxyBuilder;
import com.offgoing.mao.aliframe.annotation.control.AsyncMethod;
import com.offgoing.mao.aliframe.proxy.inter.Interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2014/12/28.
 */
public class AsyncMethodInterceptor implements Interceptor {
    private static ExecutorService pool = Executors.newCachedThreadPool();
    Handler messageProxy;

    public AsyncMethodInterceptor(Handler handler) {
        this.messageProxy = handler;
    }

    @Override
    public Object interceptor(final Object proxy,final Method method,final Object[] args) throws Throwable {
        final AsyncMethod asyncMethod = method.getAnnotation(AsyncMethod.class);
        //此时的method是需要在子线程中运行的
        if(asyncMethod!=null){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                   Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    try {
                        Object result = null;
                        result = getResult(result);
                        dealWithResult(result);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                private Object getResult(Object result) throws Throwable {
                    result = ProxyBuilder.callSuper(proxy, method, args);
                    return result;
                }
                private void dealWithResult(Object result){
                    //messageProxy.sendMessage(null);
                }
            });
            return null;
        }else{
            //正常执行一个声明的方法 此时方法在主线程中执行
            ProxyBuilder.callSuper(proxy,method,args);
        }
        return null;
    }
}
