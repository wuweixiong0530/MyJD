package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.FenLeiBean;
import com.bwie.myapplication.view.hodler.HengXiangHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

public class HengXiangAdapter extends RecyclerView.Adapter<HengXiangHolder> {
    Context context;
    FenLeiBean fenLeiBean;
    private OnItemListner onItemListner;

    public HengXiangAdapter(Context context, FenLeiBean fenLeiBean) {
        this.context = context;
        this.fenLeiBean = fenLeiBean;
    }

    @Override
    public HengXiangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.heng_xiang_item_layout, null);
        HengXiangHolder hengXiangHolder = new HengXiangHolder(view);
        return hengXiangHolder;
    }

    @Override
    public void onBindViewHolder(HengXiangHolder holder, final int position) {
        FenLeiBean.DataBean dataBean = fenLeiBean.getData().get(position);
        holder.heng_item_text.setText(dataBean.getName());
        Uri parse = Uri.parse(dataBean.getIcon());
        holder.heng_item_image.setImageURI(parse);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListner.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemListner.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return fenLeiBean.getData().size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
