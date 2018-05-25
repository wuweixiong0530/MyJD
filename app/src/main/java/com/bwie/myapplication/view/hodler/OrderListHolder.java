package com.bwie.myapplication.view.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bwie.myapplication.R;

public class OrderListHolder extends RecyclerView.ViewHolder {

    public TextView text_title;
    public TextView text_price;
    public TextView text_flag;
    public TextView text_time;
    public Button order_button;

    public OrderListHolder(View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_price = itemView.findViewById(R.id.text_price);
        text_flag = itemView.findViewById(R.id.text_flag);
        text_time = itemView.findViewById(R.id.text_time);
        order_button = itemView.findViewById(R.id.order_button);
        
    }
}
