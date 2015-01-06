package com.offgoing.mao.aliframe.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/1/3.
 */
public class Note extends BmobObject {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
