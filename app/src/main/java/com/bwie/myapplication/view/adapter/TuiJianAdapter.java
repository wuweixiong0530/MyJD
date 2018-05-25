package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.HomeBean;
import com.bwie.myapplication.view.hodler.TuiJianHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianHolder> {
    Context context;
    HomeBean.TuijianBean tuijian;
    private OnItemListner onItemListner;

    public TuiJianAdapter(Context context, HomeBean.TuijianBean tuijian) {
        this.context = context;
        this.tuijian = tuijian;
    }

    @Override
    public TuiJianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tui_jian_item_layout, null);
        TuiJianHolder tuiJianHolder = new TuiJianHolder(view);
        return tuiJianHolder;
    }

    @Override
    public void onBindViewHolder(TuiJianHolder holder, final int position) {
        HomeBean.TuijianBean.ListBean listBean = tuijian.getList().get(position);
        holder.tui_jian_item_title.setText(listBean.getTitle());
        holder.tui_jian_item_price.setText("￥" + listBean.getBargainPrice());
        String[] strings = listBean.getImages().split("\\|");
        //        Glide.with(context).load(strings[0]).into(holder.tui_jian_item_image);
        Uri parse = Uri.parse(strings[0]);
        holder.tui_jian_item_image.setImageURI(parse);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListner.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tuijian.getList().size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
