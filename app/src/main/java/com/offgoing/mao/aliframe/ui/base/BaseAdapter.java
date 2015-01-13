package com.offgoing.mao.aliframe.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/1/3.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private List<T>mDataList;
    protected Context mContext;
    protected LayoutInflater mInflater;
    public BaseAdapter(){}
    public BaseAdapter(Context mContext){
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }
    public BaseAdapter(Context mContext,List<T> mDataList) {
        this(mContext);
        this.mDataList = mDataList;
    }
    public List<T>getDataList(){
        return mDataList;
    }
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void update(List<T> mDataList){
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    /**
     * 向List追加一个实体数据 并更新
     * @param obj
     */
    public void addLastAndUpdate(T obj){
        if(mDataList==null)return;
        this.mDataList.add(obj);
        notifyDataSetChanged();
    }
}
