package com.offgoing.mao.aliframe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ToastUtil {
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
