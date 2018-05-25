package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.ChildFenLeiBean;
import com.bwie.myapplication.view.acitvity.ProductListActivity;
import com.bwie.myapplication.view.hodler.FenLeiRecyclerOutHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

public class FenLeiRecyclerOutAdapter extends RecyclerView.Adapter<FenLeiRecyclerOutHolder> {
    Context context;
    ChildFenLeiBean childFenLeiBean;

    public FenLeiRecyclerOutAdapter(Context context, ChildFenLeiBean childFenLeiBean) {
        this.context = context;
        this.childFenLeiBean = childFenLeiBean;
    }

    @Override
    public FenLeiRecyclerOutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recycler_out_item_layout, null);
        FenLeiRecyclerOutHolder fenLeiRecyclerOutHoder = new FenLeiRecyclerOutHolder(view);
        return fenLeiRecyclerOutHoder;
    }

    @Override
    public void onBindViewHolder(FenLeiRecyclerOutHolder holder, int position) {
        final ChildFenLeiBean.DataBean dataBean = childFenLeiBean.getData().get(position);
        holder.recycler_out_text.setText(dataBean.getName());
        holder.recycler_innner.setLayoutManager(new GridLayoutManager(context, 3));
        FenRecyclerInnerAdapter fenLeiRecyclerInnerAdapter = new FenRecyclerInnerAdapter(context, dataBean);
        holder.recycler_innner.setAdapter(fenLeiRecyclerInnerAdapter);
        fenLeiRecyclerInnerAdapter.setOnItemListner(new OnItemListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("keywords", dataBean.getList().get(position).getName());
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return childFenLeiBean.getData().size();
    }
}
