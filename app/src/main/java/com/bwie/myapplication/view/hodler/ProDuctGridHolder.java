package com.bwie.myapplication.view.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ProDuctGridHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView product_list_image;
    public TextView product_list_title;
    public TextView product_list_price;

    public ProDuctGridHolder(View itemView) {
        super(itemView);
        product_list_image = itemView.findViewById(R.id.product_list_image);
        product_list_title = itemView.findViewById(R.id.product_list_title);
        product_list_price = itemView.findViewById(R.id.product_list_price);
    }
}
