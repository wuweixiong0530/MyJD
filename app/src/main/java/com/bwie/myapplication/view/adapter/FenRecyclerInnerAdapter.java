package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.ChildFenLeiBean;
import com.bwie.myapplication.view.hodler.FenRecyclerInnerHolder;
import com.bwie.myapplication.view.iview.OnItemListner;

public class FenRecyclerInnerAdapter extends RecyclerView.Adapter<FenRecyclerInnerHolder> {
    Context context;
    ChildFenLeiBean.DataBean dataBean;
    private OnItemListner onItemListner;

    public FenRecyclerInnerAdapter(Context context, ChildFenLeiBean.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    @Override
    public FenRecyclerInnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fen_recycler_innner_layout, null);
        FenRecyclerInnerHolder fenLeiRecyclerInnerHolder = new FenRecyclerInnerHolder(view);
        return fenLeiRecyclerInnerHolder;
    }

    @Override
    public void onBindViewHolder(FenRecyclerInnerHolder holder, final int position) {
        ChildFenLeiBean.DataBean.ListBean listBean = dataBean.getList().get(position);
        holder.recycler_innner_text.setText(listBean.getName());
        holder.recycler_innner_image.setImageURI(listBean.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListner.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBean.getList().size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
