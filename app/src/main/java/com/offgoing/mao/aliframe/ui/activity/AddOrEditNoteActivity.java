package com.offgoing.mao.aliframe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.base.BaseActivity;
import com.offgoing.mao.aliframe.ui.control.DefaultControl;
import com.offgoing.mao.aliframe.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2015/1/2.
 */
public class AddOrEditNoteActivity extends BaseActivity<DefaultControl> {
    @InjectView(R.id.et_note_content)
    EditText mEtNoteContent;
    @InjectView(R.id.bt_save)
    Button mBtSave;
    private View rootView;
    private boolean isAdd = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_note);
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        Log.i("----","childCount:"+childCount);
        View roorView = viewGroup.getChildAt(0);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.0f, 0.0f,
                0.0f, 1.4f,
                Animation.ABSOLUTE, 0.5f,
                Animation.ABSOLUTE, 0.5f);
        scaleAnimation.setDuration(1000);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        roorView.setAnimation(animationSet);
        ButterKnife.inject(this);
        initView();
    }
    private void initView(){
        Note note = (Note)getIntent().getSerializableExtra("note");
        if(note!=null){
            isAdd = false;
            initActionBar(true,R.string.title_activity_edit_note);
            mEtNoteContent.setText(note.getContent());
            mEtNoteContent.setSelection(note.getContent().length());
        }else{
            isAdd = true;
            initActionBar(true,R.string.title_activity_add_note);
        }
    }
    @OnClick(R.id.bt_save)
    public void onClickSave(){
        String content = mEtNoteContent.getText().toString().trim();
        if(TextUtils.isEmpty(content))return;
        if(isAdd){
            Note note = new Note();
            note.setContent(content);
            addNote(note);
        }else{
            Note note = (Note)getIntent().getSerializableExtra("note");
            note.setContent(content);
            updateNote(note);
        }
    }
    private void addNote(final Note note){
        note.save(this,new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(AddOrEditNoteActivity.this,"save suc");
                Intent intent = new Intent();
                intent.putExtra("note",note);
                setResult(MainActivity.RESULT_ADD_NOTE_SUC,intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.showToast(AddOrEditNoteActivity.this,"save fail "+s);
            }
        });
    }
    private void updateNote(final Note note){
        note.update(this,new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(AddOrEditNoteActivity.this,"update suc");
                Intent intent = new Intent();
                intent.putExtra("note",note);
                setResult(MainActivity.RESULT_EDIT_NOTE_SUC,intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.showToast(AddOrEditNoteActivity.this,"update fail "+s);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
