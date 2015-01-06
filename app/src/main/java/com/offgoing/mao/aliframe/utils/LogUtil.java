package com.offgoing.mao.aliframe.utils;


import android.util.Log;

import com.offgoing.mao.aliframe.constact.Global;

/**
 * Created by Administrator on 2014/12/28.
 */
public final class LogUtil {
    public static void i(Class clazz,String msg){
        if(Global.DEBUG)
        Log.i(clazz.getSimpleName(),msg);
    }
    public static void i(String tag,String msg){
        if(Global.DEBUG)
        Log.i(tag,msg);
    }
}
