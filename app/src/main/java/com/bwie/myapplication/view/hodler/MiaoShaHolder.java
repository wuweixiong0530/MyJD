package com.bwie.myapplication.view.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class MiaoShaHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView imageView;
    public TextView text_price;

    public MiaoShaHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.miao_sha_image);
        text_price = itemView.findViewById(R.id.text_price);
    }
}
