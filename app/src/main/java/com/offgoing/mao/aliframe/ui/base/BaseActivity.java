/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.offgoing.mao.aliframe.ui.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.annotation.view.EventListener;
import com.offgoing.mao.aliframe.annotation.view.Select;
import com.offgoing.mao.aliframe.annotation.view.ViewInject;
import com.offgoing.mao.aliframe.proxy.ControlFactory;
import com.offgoing.mao.aliframe.proxy.MsgParam;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

public abstract class BaseActivity<T extends BaseControl> extends ActionBarActivity {
    /**
     * Activity的业务控制中心，推荐把所有业务逻辑代码写在Control中
     * 实现这样的功能，在Activity中利用mControl 去发送一个异步请求 然后执行完成后 给一个回调
     * 回调的具体实现是在Activity中完成，这里涉及到了Handler、Message、反射，注解
     * 具体思路：在实例化Activity时 应该创建一个Control实例 这个Control就是一些具有相似性的业务
     * 的几个控制集合，具体的业务逻辑应该在具体的Control中，这个实例中应该有一个Handler对象
     * 还有这个Control应该是一个基类
     */
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }
    private void initParam(){
        Class clazz = getClass();
        Type genType = clazz.getGenericSuperclass();
        //根据泛型实例化Control类
        if(genType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)genType;
            Type[]params = parameterizedType.getActualTypeArguments();
            Class<T>tClass = (Class<T>)params[0];
            initControlInstanceV2(tClass);
        }
    }
    private void initControlInstanceV2(Class<T>tClass){
        mControl = ControlFactory.getControlInstance(tClass,mHandler);
        mModel = new HashMap<Object,Object>();
        mControl.setModel(mModel);
        mControl.setContext(this);
    }
    /**
     * @param layoutResID
     */
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		//initInjectedView(this);
	}


	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initInjectedView(this);
	}


	public void setContentView(View view) {
		super.setContentView(view);
		initInjectedView(this);
	}
	

	public static void initInjectedView(Activity activity){
		initInjectedView(activity, activity.getWindow().getDecorView());
	}
	
	
	public static void initInjectedView(Object injectedSource,View sourceView){
		Field[] fields = injectedSource.getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				try {
					field.setAccessible(true);
					
					if(field.get(injectedSource)!= null )
						continue;
				
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if(viewInject!=null){
						
						int viewId = viewInject.id();
					    field.set(injectedSource,sourceView.findViewById(viewId));
					
					    setListener(injectedSource,field,viewInject.click(),Method.Click);
						setListener(injectedSource,field,viewInject.longClick(),Method.LongClick);
						setListener(injectedSource,field,viewInject.itemClick(),Method.ItemClick);
						setListener(injectedSource,field,viewInject.itemLongClick(),Method.itemLongClick);
						
						Select select = viewInject.select();
						if(!TextUtils.isEmpty(select.selected())){
							setViewSelectListener(injectedSource,field,select.selected(),select.noSelected());
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static void setViewSelectListener(Object injectedSource,Field field,String select,String noSelect)throws Exception{
		Object obj = field.get(injectedSource);
		if(obj instanceof View){
			((AbsListView)obj).setOnItemSelectedListener(new EventListener(injectedSource).select(select).noSelect(noSelect));
		}
	}
	
	
	private static void setListener(Object injectedSource,Field field,String methodName,Method method)throws Exception{
		if(methodName == null || methodName.trim().length() == 0)
			return;
		
		Object obj = field.get(injectedSource);
		
		switch (method) {
			case Click:
				if(obj instanceof View){
					((View)obj).setOnClickListener(new EventListener(injectedSource).click(methodName));
				}
				break;
			case ItemClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemClickListener(new EventListener(injectedSource).itemClick(methodName));
				}
				break;
			case LongClick:
				if(obj instanceof View){
					((View)obj).setOnLongClickListener(new EventListener(injectedSource).longClick(methodName));
				}
				break;
			case itemLongClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemLongClickListener(new EventListener(injectedSource).itemLongClick(methodName));
				}
				break;
			default:
				break;
		}
	}
	
	public enum Method{
		Click,LongClick,ItemClick,itemLongClick
	}
	public void initActionBar(boolean displayHomeAsUpEnabled,int title){
//        ActionBar actionBar =  getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
//        actionBar.setTitle(title);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_awesome_toolbar);
        if(toolbar==null)return;
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
    }
}
