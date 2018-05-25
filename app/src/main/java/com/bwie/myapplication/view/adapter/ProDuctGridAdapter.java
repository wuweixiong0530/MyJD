package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.ProductListBean;
import com.bwie.myapplication.view.hodler.ProDuctGridHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

import java.util.List;

public class ProDuctGridAdapter extends RecyclerView.Adapter<ProDuctGridHolder> {
    private List<ProductListBean.DataBean> listAll;
    private Context context;
    private OnItemListner onItemListner;

    public ProDuctGridAdapter(Context context, List<ProductListBean.DataBean> listAll) {
        this.context = context;
        this.listAll = listAll;
    }

    @Override
    public ProDuctGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.product_gird_item_layout, null);
        ProDuctGridHolder proDuctGridHolder = new ProDuctGridHolder(view);
        return proDuctGridHolder;
    }

    @Override
    public void onBindViewHolder(ProDuctGridHolder holder, final int position) {
        ProductListBean.DataBean dataBean = listAll.get(position);

        //赋值
        holder.product_list_title.setText(dataBean.getTitle());
        holder.product_list_price.setText("¥" + dataBean.getBargainPrice());
        holder.product_list_image.setImageURI(dataBean.getImages().split("\\|")[0]);

        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListner.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAll.size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
