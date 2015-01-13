package com.offgoing.mao.aliframe.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.ui.control.NavigationManager;
import com.offgoing.mao.aliframe.ui.fragment.NoteListFragment;
import com.offgoing.mao.aliframe.ui.base.BaseActivity;
import com.offgoing.mao.aliframe.ui.control.DefaultControl;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity<DefaultControl> {
    public static final int RESULT_ADD_NOTE_SUC = 1;
    public static final int RESULT_EDIT_NOTE_SUC = 2;
    @InjectView(R.id.ll_slide_menu)
    LinearLayout mLlSlideMenu;
    @InjectView(R.id.dl_main_container)
    DrawerLayout mDlMainContainer;
    ActionBarDrawerToggle mDrawerToggle;
    protected ImageButton mIbAdd;
    private NoteListFragment mNoteListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        addListener();
    }

    private void initView() {
        initActionBar(true, R.string.app_name);
        initDrawerToggle();
        mDlMainContainer.setDrawerListener(mDrawerToggle);
        addNoteList();
        mIbAdd = addAddView();
    }
    private void addListener(){
        mIbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddFragment();
            }
        });
    }
    public void goToAddFragment(){
        NavigationManager.gotoAddNoteActivity(this);
    }
    private void addNoteList() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mNoteListFragment = new NoteListFragment();
        fragmentTransaction.replace(R.id.fl_container,mNoteListFragment, "123");
        fragmentTransaction.commit();
    }

    /**
     * 添加位于底部Add按钮
     */
    private ImageButton addAddView(){
        mIbAdd = new ImageButton(this);
        mIbAdd.setBackgroundResource(R.drawable.selector_add);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.RIGHT;
        params.bottomMargin = 20;
        params.rightMargin = 20;
        mIbAdd.setLayoutParams(params);
        ((ViewGroup) getWindow().getDecorView()).addView(mIbAdd);
        return mIbAdd;
    }
    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDlMainContainer,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_up_description) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
            }
        };
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mNoteListFragment!=null){
            mNoteListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
