package com.offgoing.mao.aliframe.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.activity.MainActivity;
import com.offgoing.mao.aliframe.ui.adapter.NoteListAdapter;
import com.offgoing.mao.aliframe.ui.base.BaseFragment;
import com.offgoing.mao.aliframe.ui.control.DefaultControl;
import com.offgoing.mao.aliframe.ui.control.NavigationManager;
import com.offgoing.mao.aliframe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2015/1/2.
 */
public class NoteListFragment extends BaseFragment<DefaultControl>{
    ListView mLvList;
    private View rootView;
    private NoteListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_note_list, null);
            mLvList = ButterKnife.findById(rootView, R.id.lv_list);
            ButterKnife.inject(this,rootView);
            //ButterKnife.findById(rootView, R.id.tv_add).setOnClickListener(this);
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
        ToastUtil.showToast(getActivity(), "getNoteOnError");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case MainActivity.RESULT_ADD_NOTE_SUC:
                if(data==null)return;
                Note addNote = (Note)data.getSerializableExtra("note");
                mAdapter.addLastAndUpdate(addNote);
            break;
            case MainActivity.RESULT_EDIT_NOTE_SUC:
                if(data==null)return;
                Note editNote = (Note)data.getSerializableExtra("note");
                int location = getLocation(editNote);
                if(location>=0){
                    mAdapter.getDataList().remove(location);
                    mAdapter.getDataList().add(location,editNote);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    private int getLocation(Note note){
        if(note==null)return -1;
        List<Note>noteList = mAdapter.getDataList();
        for(int i = 0;i<noteList.size();i++){
            if(noteList.get(i).equals(note)){
               return i;
            }
        }
        return -1;
    }
    @OnItemClick(R.id.lv_list)void onListItemClick(AdapterView<?> parent, View view, int position, long id){
        Note note = ((NoteListAdapter)parent.getAdapter()).getItem(position);
        NavigationManager.gotoEditNoteActivity(getActivity(),note);
    }

    @OnClick(R.id.tv_add)void onAdd(){
        NavigationManager.gotoAddNoteActivity(getActivity());
    }

}
