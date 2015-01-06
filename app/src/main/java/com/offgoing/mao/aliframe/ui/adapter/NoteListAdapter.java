package com.offgoing.mao.aliframe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.base.BaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/2.
 */
public class NoteListAdapter extends BaseAdapter<Note> {
    public NoteListAdapter(Context mContext,List<Note> mDataList) {
        super(mContext,mDataList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_list_note, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.mTvContent.setText(getItem(position).getContent());
        return convertView;
    }
    static class ViewHolder {
        @InjectView(R.id.tv_content)
        TextView mTvContent;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
