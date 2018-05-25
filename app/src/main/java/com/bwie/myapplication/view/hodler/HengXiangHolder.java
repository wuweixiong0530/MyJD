package com.bwie.myapplication.view.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class HengXiangHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView heng_item_image;
    public TextView heng_item_text;

    public HengXiangHolder(View itemView) {
        super(itemView);
        heng_item_image = itemView.findViewById(R.id.heng_item_image);
        heng_item_text = itemView.findViewById(R.id.heng_item_text);
    }
}
