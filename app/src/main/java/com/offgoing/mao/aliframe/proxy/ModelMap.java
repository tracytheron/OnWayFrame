package com.offgoing.mao.aliframe.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/27.
 */
public class ModelMap {
    private Map map;
    public ModelMap(){
        map = new HashMap();
    }
    public<T>void put(T key,Object obj){
        map.put(key,obj);
    }
    public <T> void put(String key,Object obj){
        map.put(key,obj);
    }
}
