package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.myapplication.view.SouSuoSql.MySearBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2,0002.
 */

public class GreenDaoAdapter extends BaseAdapter {
    private List<MySearBean> list;
    private Context context;

    public GreenDaoAdapter(Context context, List<MySearBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context,android.R.layout.simple_list_item_1,null);
        }
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(list.get(i).getName());
        return view;
    }
}
