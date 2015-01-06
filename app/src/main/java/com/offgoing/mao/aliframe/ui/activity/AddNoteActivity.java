package com.offgoing.mao.aliframe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.constact.Global;
import com.offgoing.mao.aliframe.entity.Note;
import com.offgoing.mao.aliframe.ui.base.BaseActivity;
import com.offgoing.mao.aliframe.ui.control.DefaultControl;
import com.offgoing.mao.aliframe.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2015/1/2.
 */
public class AddNoteActivity extends BaseActivity<DefaultControl> {
    @InjectView(R.id.et_note_content)
    EditText mEtNoteContent;
    @InjectView(R.id.bt_save)
    Button mBtSave;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_note);
        ButterKnife.inject(this);
        initActionBar(true,R.string.title_activity_add_note);
    }
    @OnClick(R.id.bt_save)
    public void onClickSave(){
        String content = mEtNoteContent.getText().toString().trim();
        if(TextUtils.isEmpty(content))return;
        final Note note = new Note();
        note.setContent(content);
        note.save(this,new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(AddNoteActivity.this,"save suc");
                Intent intent = new Intent();
                intent.putExtra("note",note);
                setResult(MainActivity.RESULT_ADD_NOTE_SUC,intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.showToast(AddNoteActivity.this,"save fail");
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
