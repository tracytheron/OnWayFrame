package com.offgoing.mao.aliframe.ui.control;

import android.os.Handler;

import com.offgoing.mao.aliframe.annotation.control.AsyncMethod;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.base.BaseControl;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2014/12/25.
 */
public class DefaultControl extends BaseControl {
    public DefaultControl(Handler handler){
        super(handler);
    }
    public void getNoteList(){
        BmobQuery<Note>bmobQuery = new BmobQuery<Note>();
        bmobQuery.findObjects(mContext,new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> notes) {
                mModel.put("list",notes);
                sendMessage("getNoteOnSuccess");
            }

            @Override
            public void onError(int i, String s) {
                mModel.put("i",i);
                mModel.put("s",s);
                sendMessage("getNoteOnError");
            }
        });
    }
    public void getNoteDirectly(){
        List<String> listData = new ArrayList<String>();
        listData.add("123");
        mModel.put("list",listData);
        sendMessage("getNoteDirectlyFinish");
    }
}
