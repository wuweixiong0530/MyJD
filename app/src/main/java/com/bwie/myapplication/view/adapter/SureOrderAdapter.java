package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.CartBean;
import com.bwie.myapplication.view.hodler.SureOrderHolder;

import java.util.ArrayList;

public class SureOrderAdapter extends RecyclerView.Adapter<SureOrderHolder> {
    Context context;
    ArrayList<CartBean.DataBean.ListBean> list_selected;

    public SureOrderAdapter(Context context, ArrayList<CartBean.DataBean.ListBean> list_selected) {
        this.context = context;
        this.list_selected = list_selected;
    }

    @Override
    public SureOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.sure_order_item_layout, null);
        SureOrderHolder sureOrderHolder = new SureOrderHolder(view);
        return sureOrderHolder;
    }

    @Override
    public void onBindViewHolder(SureOrderHolder holder, int position) {
        CartBean.DataBean.ListBean listBean = list_selected.get(position);
//        Glide.with(context).load(listBean.getImages().split("\\|")).into(holder.sure_item_image);
        holder.sure_item_title.setText(listBean.getTitle());
        holder.sure_item_price.setText("￥" + listBean.getBargainPrice());
        holder.sure_item_num.setText("x" + listBean.getNum());
    }

    @Override
    public int getItemCount() {
        return list_selected.size();
    }
}
