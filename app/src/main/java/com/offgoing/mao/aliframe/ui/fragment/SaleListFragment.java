package com.offgoing.mao.aliframe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.offgoing.mao.aliframe.FinalFragment;
import com.offgoing.mao.aliframe.R;

/**
 * Created by Administrator on 2014/12/21.
 */
public class SaleListFragment extends FinalFragment{
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_valuation,container,false);
        listView = (ListView)view.findViewById(R.id.lv_list_valuation);
        String[]array = getResources().getStringArray(R.array.sale);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,array));
        return view;
    }
}
