package com.offgoing.mao.aliframe;

import com.offgoing.mao.aliframe.constact.Global;
import com.offgoing.mao.aliframe.proxy.ControlFactory;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ControlFactory.init(getApplicationContext());
        Bmob.initialize(this, Global.BMOB_APPLICATION_ID);
    }
}
