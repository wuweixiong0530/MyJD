package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.HomeBean;
import com.bwie.myapplication.view.hodler.MiaoShaHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

public class MiaoShaAdapter extends RecyclerView.Adapter<MiaoShaHolder> {
    Context context;
    HomeBean.MiaoshaBean miaosha;
    private OnItemListner onItemListner;

    public MiaoShaAdapter(Context context, HomeBean.MiaoshaBean miaosha) {
        this.context = context;
        this.miaosha = miaosha;
    }

    @Override
    public MiaoShaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.miao_sha_item_layout, null);
        MiaoShaHolder miaoShaHolder = new MiaoShaHolder(view);
        return miaoShaHolder;
    }

    @Override
    public void onBindViewHolder(MiaoShaHolder holder, final int position) {
        String[] strings = miaosha.getList().get(position).getImages().split("\\|");
        Uri parse = Uri.parse(strings[0]);
        holder.imageView.setImageURI(parse);
        holder.text_price.setText("￥"+miaosha.getList().get(position).getBargainPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListner.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return miaosha.getList().size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
