package com.offgoing.mao.aliframe.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.activity.AddNoteActivity;
import com.offgoing.mao.aliframe.ui.activity.MainActivity;
import com.offgoing.mao.aliframe.ui.adapter.NoteListAdapter;
import com.offgoing.mao.aliframe.ui.base.BaseFragment;
import com.offgoing.mao.aliframe.ui.control.DefaultControl;
import com.offgoing.mao.aliframe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2015/1/2.
 */
public class NoteListFragment extends BaseFragment<DefaultControl> {
    ListView mLvList;
    private View rootView;
    private NoteListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_note_list, null);
            mLvList = ButterKnife.findById(rootView,R.id.lv_list);
            mControl.getNoteList();
        }else{
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }
    public void getNoteOnSuccess(){
        List<Note>list = (ArrayList<Note>)mModel.get("list");
        if(mAdapter==null){
            mAdapter = new NoteListAdapter(getActivity(),list);
            mLvList.setAdapter(mAdapter);
        }else{
            mAdapter.update(list);
        }
    }
    public void getNoteOnError(){
        ToastUtil.showToast(getActivity(),"getNoteOnError");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case MainActivity.RESULT_ADD_NOTE_SUC:
                if(data==null)return;
                Note note = (Note)data.getSerializableExtra("note");
                mAdapter.update(note);
            break;
        }
    }
}
