package com.offgoing.mao.aliframe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.offgoing.mao.aliframe.R;

/**
 * Created by jh on 2015/1/21.
 */
public class AddOrEditFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Detail");
        //toolbar.setLogo(R.drawable.icon_close);
        //setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_close);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                if(menuItem.getItemId()==android.R.id.home){
//                    return true;
//                }else{
//                    return false;
//                }
//            }
//        });
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_add_note,null);
        }else{
            ((ViewGroup)(rootView.getParent())).removeView(rootView);
        }
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
