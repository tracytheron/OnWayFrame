package com.offgoing.mao.aliframe.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.offgoing.mao.aliframe.proxy.MsgParam;

import java.util.Map;

/**
 * Created by Administrator on 2014/12/25.
 */
public class BaseControl {
    //控制器和视图层的数据传递中介
    protected Map<Object,Object> mModel;
    protected Context mContext;
    public Map<Object, Object> getModel() {
        return mModel;
    }

    private Handler mHandler;
    public BaseControl(Handler handler) {
        this.mHandler = handler;
    }

    public void setModel(Map<Object, Object> mModel) {
        this.mModel = mModel;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void sendMessage(String msg){
        if(TextUtils.isEmpty(msg))return;
        Message message = mHandler.obtainMessage();
        message.what = MsgParam.MAS_WHAT_BANKGROUND_THREAD;
        message.obj = msg;
        mHandler.sendMessage(message);
    }
}
