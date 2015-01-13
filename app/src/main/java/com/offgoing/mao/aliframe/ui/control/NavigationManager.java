package com.offgoing.mao.aliframe.ui.control;

import android.app.Activity;
import android.content.Intent;

import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.activity.AddOrEditNoteActivity;

/**
 * 所有界面的跳转逻辑尽可能的写在这里
 * Created by Administrator on 2015/1/3.
 */
public class NavigationManager {
    public static void gotoAddNoteActivity(Activity context){
        Intent intent = new Intent(context,AddOrEditNoteActivity.class);
        context.startActivityForResult(intent,1);
    }
    public static void gotEditNoteActivity(Activity context,Note note){
        Intent intent = new Intent(context,AddOrEditNoteActivity.class);
        intent.putExtra("note",note);
        context.startActivityForResult(intent,1);
    }
}
