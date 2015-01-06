package com.offgoing.mao.aliframe.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.offgoing.mao.aliframe.R;
import com.offgoing.mao.aliframe.ui.fragment.SaleListFragment;
import com.offgoing.mao.aliframe.ui.fragment.ValuationListFragment;

/**
 * 测试Fragment 相关的Activity
 */
public class FragmentActivityDemo extends FragmentActivity {
    private Button btOne;
    private Button btTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initWidget();
        btTwo.post(new Runnable() {
            @Override
            public void run() {
                controlShow(0);
            }
        });

    }
    private int currentItem = -1;
    private void initFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_container,new ValuationListFragment(),0+"");
        fragmentTransaction.add(R.id.fl_container,new SaleListFragment(),1+"");
        fragmentTransaction.commit();
    }
    private void controlShow(int position){
        // if(currentItem == position)return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment oldFragmentOne = fragmentManager.findFragmentByTag(0+"");
        Fragment oldFragmentTwo = fragmentManager.findFragmentByTag(1+"");
        if(oldFragmentOne!=null && oldFragmentTwo!=null){
            if(position==0){
                fragmentTransaction.show(oldFragmentOne);
                fragmentTransaction.hide(oldFragmentTwo);
            }else{
                fragmentTransaction.show(oldFragmentTwo);
                fragmentTransaction.hide(oldFragmentOne);
            }
            fragmentTransaction.commit();
        }
    }
    private void initWidget(){
        btOne = (Button)findViewById(R.id.bt_one);
        btTwo = (Button)findViewById(R.id.bt_two);

        btOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlShow(0);
            }
        });
        btTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlShow(1);
            }
        });
    }
    private void widgetClick(View view){
        switch (view.getId()){
            case R.id.bt_one:
                break;
            case R.id.bt_two:
                break;
        }
    }
}