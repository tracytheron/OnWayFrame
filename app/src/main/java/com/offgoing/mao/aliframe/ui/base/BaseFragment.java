package com.offgoing.mao.aliframe.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.offgoing.mao.aliframe.proxy.ControlFactory;
import com.offgoing.mao.aliframe.proxy.MsgParam;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/3.
 */
public abstract class BaseFragment<T extends BaseControl> extends Fragment {
    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MsgParam.MAS_WHAT_BANKGROUND_THREAD:
                    String method = null;
                    if(msg.obj instanceof String){
                        method = (String)msg.obj;
                        invokeMethod(method);
                    }
                    break;
            }
        }
    };
    protected T mControl;
    /**
     * 控制器和视图层之间的数据传递介质
     */
    protected Map<Object,Object> mModel;
    private void invokeMethod(String methodName){
        try {
            java.lang.reflect.Method method = getClass().getMethod(methodName);
            if(method!=null){
                method.setAccessible(true);
                method.invoke(this,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }
    private void initParam(){
        Class clazz = getClass();
        Type genType = clazz.getGenericSuperclass();
        if(genType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)genType;
            Type[]params = parameterizedType.getActualTypeArguments();
            Class<T>tClass = (Class<T>)params[0];
            initControlInstanceV2(tClass);
        }
    }
    private void initControlInstanceV2(Class<T>tClass){
        mControl = ControlFactory.getControlInstance(tClass, mHandler);
        mModel = new HashMap<Object,Object>();
        mControl.setModel(mModel);
        mControl.setContext(getActivity());
    }
}
