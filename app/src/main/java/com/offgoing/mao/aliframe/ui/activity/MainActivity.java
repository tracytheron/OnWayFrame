package com.offgoing.mao.aliframe.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.offgoing.mao.aliframe.R;
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
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private NoteListFragment mNoteListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //initActionBar(true, R.string.app_name);
        //initDrawerToggle();

        initActionBar();

        addNoteList();
    }
    private void initActionBar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("TodoList");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void addNoteList() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mNoteListFragment = new NoteListFragment();
        fragmentTransaction.replace(R.id.fl_container,mNoteListFragment, "123");
        fragmentTransaction.commit();
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,
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
