package com.offgoing.mao.aliframe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.offgoing.mao.aliframe.annotation.view.EventListener;
import com.offgoing.mao.aliframe.annotation.view.Select;
import com.offgoing.mao.aliframe.annotation.view.ViewInject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2014/12/21.
 */
public class FinalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initInjectedView(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
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
}
