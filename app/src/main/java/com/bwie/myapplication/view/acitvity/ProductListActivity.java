package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.MessageBean;
import com.bwie.myapplication.model.bean.ProductListBean;
import com.bwie.myapplication.presenter.ProductListPresenter;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.adapter.ProDuctGridAdapter;
import com.bwie.myapplication.view.adapter.ProDuctListAdapter;
import com.bwie.myapplication.view.iview.OnItemListner;
import com.bwie.myapplication.view.iview.ProductListActivityInter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ProductListActivity extends AppCompatActivity implements ProductListActivityInter {

    @BindView(R.id.product_image_back)
    ImageView product_image_back;
    @BindView(R.id.linear_search)
    LinearLayout linear_search;
    @BindView(R.id.image_change)
    ImageView image_change;
    @BindView(R.id.product_list_recycler)
    RecyclerView product_list_recycler;
    @BindView(R.id.product_grid_recycler)
    RecyclerView product_grid_recycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ProductListPresenter productListPresenter;
    private String keywords;
    private int page = 1;
    private List<ProductListBean.DataBean> listAll = new ArrayList<>();
    private ProDuctListAdapter proDuctListAdapter;
    private ProDuctGridAdapter proDuctGridAdapter;
    private boolean isList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        //注册EventBUs
        EventBus.getDefault().register(this);

        productListPresenter = new ProductListPresenter(this);

        if (keywords != null) {
            productListPresenter.getProductData(Constant.SEARTCH_URL, keywords, page);
        }
        product_list_recycler.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
        product_grid_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                listAll.clear();
                productListPresenter.getProductData(Constant.SEARTCH_URL, keywords, page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                productListPresenter.getProductData(Constant.SEARTCH_URL, keywords, page);
            }
        });
    }

    //EventBus传过来的值（sticky = true  使粘性事件为true)
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(MessageBean event) {

        keywords = event.getName();

    }

    @OnClick({R.id.product_image_back, R.id.linear_search, R.id.image_change})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_search:
                Toast.makeText(this, "即将跳转搜索...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_image_back:
                finish();
                break;
            case R.id.image_change:

                if (isList) {//表示当前展示的是列表..图标变成列表样式...表格进行显示,列表隐藏...isList---false
                    image_change.setImageResource(R.drawable.kind_liner);

                    product_grid_recycler.setVisibility(View.VISIBLE);
                    product_list_recycler.setVisibility(View.GONE);

                    isList = false;
                } else {
                    image_change.setImageResource(R.drawable.kind_grid);

                    product_list_recycler.setVisibility(View.VISIBLE);
                    product_grid_recycler.setVisibility(View.GONE);

                    isList = true;
                }

                break;
        }
    }

    @Override
    public void getProductDataSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            Log.e("sss", string);
            ProductListBean productListBean = new Gson().fromJson(string, ProductListBean.class);
            listAll.addAll(productListBean.getData());
            setAdapter();
            proDuctListAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ProductListActivity.this, DetailActivity.class);
                    intent.putExtra("pid", listAll.get(position).getPid());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position) {

                }
            });
            proDuctGridAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ProductListActivity.this, DetailActivity.class);
                    intent.putExtra("pid", listAll.get(position).getPid());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        if (proDuctListAdapter == null) {
            proDuctListAdapter = new ProDuctListAdapter(ProductListActivity.this, listAll);
            product_list_recycler.setAdapter(proDuctListAdapter);
        } else {
            proDuctListAdapter.notifyDataSetChanged();
        }
        if (proDuctGridAdapter == null) {
            proDuctGridAdapter = new ProDuctGridAdapter(ProductListActivity.this, listAll);
            product_grid_recycler.setAdapter(proDuctGridAdapter);
        } else {
            proDuctGridAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销
        EventBus.getDefault().unregister(this);
    }
}
