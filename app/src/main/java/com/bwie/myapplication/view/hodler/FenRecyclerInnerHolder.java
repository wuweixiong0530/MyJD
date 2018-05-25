package com.bwie.myapplication.view.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class FenRecyclerInnerHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView recycler_innner_image;
    public TextView recycler_innner_text;

    public FenRecyclerInnerHolder(View itemView) {
        super(itemView);
        recycler_innner_image = itemView.findViewById(R.id.recycler_innner_image);
        recycler_innner_text = itemView.findViewById(R.id.recycler_innner_text);
    }
}
