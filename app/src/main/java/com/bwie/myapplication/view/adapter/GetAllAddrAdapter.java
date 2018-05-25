package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.GetAllAddrBean;

import java.util.List;

public class GetAllAddrAdapter extends BaseAdapter {
    private List<GetAllAddrBean.DataBean> data;
    private Context context;

    public GetAllAddrAdapter(Context context, List<GetAllAddrBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.get_all_addr_item_layout, null);
            holder = new ViewHolder();

            holder.text_name = view.findViewById(R.id.text_name);
            holder.text_phone = view.findViewById(R.id.text_phone);
            holder.text_addr = view.findViewById(R.id.text_addr);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        GetAllAddrBean.DataBean dataBean = data.get(position);
        holder.text_name.setText(dataBean.getName());
        holder.text_phone.setText(String.valueOf(dataBean.getMobile()));

        //status为1是默认地址
        if (dataBean.getStatus() == 1) {
            holder.text_addr.setText("[默认地址] " + dataBean.getAddr());
        } else {
            holder.text_addr.setText(dataBean.getAddr());
        }

        return view;
    }

    private class ViewHolder {
        TextView text_name;
        TextView text_phone;
        TextView text_addr;
    }
}
