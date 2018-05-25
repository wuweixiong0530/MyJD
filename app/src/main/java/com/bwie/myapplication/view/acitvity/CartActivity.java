package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.CartBean;
import com.bwie.myapplication.model.bean.CountPriceBean;
import com.bwie.myapplication.model.bean.HomeBean;
import com.bwie.myapplication.presenter.FragmentCartPresenter;
import com.bwie.myapplication.presenter.FragmentHomeP;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.adapter.MyExpanableAdapter;
import com.bwie.myapplication.view.adapter.TuiJianAdapter;
import com.bwie.myapplication.view.custom.MyExpanableView;
import com.bwie.myapplication.view.iview.FragmentCartInter;
import com.bwie.myapplication.view.iview.InterFragmentHome;
import com.bwie.myapplication.view.iview.OnItemListner;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class CartActivity extends AppCompatActivity implements InterFragmentHome, FragmentCartInter {

    @BindView(R.id.detail_image_back)
    ImageView detail_image_back;
    @BindView(R.id.text_title)
    RelativeLayout text_title;
    @BindView(R.id.my_expanable_view)
    MyExpanableView my_expanable_view;
    @BindView(R.id.empty_cart_image)
    ImageView empty_cart_image;
    @BindView(R.id.cart_login)
    Button cart_login;
    @BindView(R.id.linear_login)
    LinearLayout linear_login;
    @BindView(R.id.tui_jian_recycler)
    RecyclerView tui_jian_recycler;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.relative_progress)
    RelativeLayout relative_progress;
    @BindView(R.id.cart_check_all)
    CheckBox cart_check_all;
    @BindView(R.id.text_total)
    TextView text_total;
    @BindView(R.id.text_buy)
    TextView text_buy;
    @BindView(R.id.linear_bottom)
    LinearLayout linear_bottom;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                CountPriceBean countPriceBean = (CountPriceBean) msg.obj;
                //设置显示
                text_total.setText("合计：￥" + countPriceBean.getPriceString());
                text_buy.setText("去结算（" + countPriceBean.getCount() + "）");
            }
        }
    };
    private FragmentHomeP fragmentHomeP;
    private FragmentCartPresenter fragmentCartPresenter;
    private MyExpanableAdapter myExpanableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart_layout);
        ButterKnife.bind(this);
        detail_image_back.setVisibility(View.VISIBLE);
        //去掉默认的指示器
        my_expanable_view.setGroupIndicator(null);
        //失去焦点....界面不是从recyclerView开始显示
        tui_jian_recycler.setFocusable(false);
        //2.为你推荐,,,只需要获取一次
        fragmentHomeP = new FragmentHomeP(this);
        //调用p层里面的方法....https://www.zhaoapi.cn/ad/getAd
        fragmentHomeP.getNetData(Constant.HOME_URL);
        fragmentCartPresenter = new FragmentCartPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //为你推荐的数据请求一次....其实为你推荐的数据也是随着登录和不登录改变
        initData();
    }

    private void initData() {
        //判断是否登录...没有,则显示登录按钮....已经登录显示购物车数据
        if (CommonUtil.getBoolean("isLogin")) {
            //请求购物车的数据...显示购物车
            my_expanable_view.setVisibility(View.VISIBLE);
            linear_login.setVisibility(View.GONE);
            //请求购物车的数据
            getCartData();
        } else {
            //登录按钮的显示
            linear_login.setVisibility(View.VISIBLE);
            my_expanable_view.setVisibility(View.GONE);
            //登录的点击事件
            cart_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
//获取购物车数据
    private void getCartData() {
        //显示进度条
        relative_progress.setVisibility(View.VISIBLE);
        //查询购物车的数据
        fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
    }
//    是否所有的组选中
    private boolean isAllGroupChecked(CartBean cartBean) {
        for (int i = 0; i < cartBean.getData().size(); i++) {
            //组没有选中
            if (!cartBean.getData().get(i).isGroupChecked()) {
                return false;
            }
        }
        return true;
    }
//    判断组中所有的子孩子是否全部选中
    private boolean isAllChildInGroupChecked(List<CartBean.DataBean.ListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    @OnClick({R.id.detail_image_back, R.id.cart_check_all})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cart_check_all:  //全选 设置点击事件
                //点击全选的时候,,,,根据全选的状态 改变购物车所有商品的选中状态
                myExpanableAdapter.setAllChildChecked(cart_check_all.isChecked());
                break;
            case R.id.detail_image_back:
                finish();
                break;
        }
    }

    @Override
    public void getCartDataSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final CartBean cartBean = new Gson().fromJson(string, CartBean.class);
            //进度条隐藏
            relative_progress.setVisibility(View.GONE);
            empty_cart_image.setVisibility(View.GONE);
            //显示购物车的数据....二级列表设置适配器
            //1.根据某一组里面所有子孩子是否选中,决定当前组是否选中
            for (int i = 0; i < cartBean.getData().size(); i++) {
                //遍历每一组的数据,,,设置是否选中...有所有的子孩子决定
                CartBean.DataBean dataBean = cartBean.getData().get(i);
                dataBean.setGroupChecked(isAllChildInGroupChecked(dataBean.getList()));
            }
            //2.根据所有的组是否选中,,,决定全选是否选中
            cart_check_all.setChecked(isAllGroupChecked(cartBean));
            myExpanableAdapter = new MyExpanableAdapter(CartActivity.this, cartBean, handler, relative_progress, fragmentCartPresenter);
            my_expanable_view.setAdapter(myExpanableAdapter);
            //展开所有的每一组
            for (int i = 0; i < cartBean.getData().size(); i++) {
                my_expanable_view.expandGroup(i);
            }
            //4.计算商品的总价和数量
            myExpanableAdapter.sendPriceAndCount();
            //子条目的点击事件
            my_expanable_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                    //跳转的是自己的详情页面
                    Intent intent = new Intent(CartActivity.this, DetailActivity.class);
                    //传递pid
                    intent.putExtra("pid", cartBean.getData().get(groupPosition).getList().get(childPosition).getPid());
                    startActivity(intent);
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartDataNull() {
        relative_progress.setVisibility(View.GONE);
        empty_cart_image.setVisibility(View.VISIBLE);
        Toast.makeText(CartActivity.this, "购物车为空，先逛逛吧", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessHome(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
            //瀑布流
            tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
            //为你推荐设置适配器
            TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(CartActivity.this, homeBean.getTuijian());
            tui_jian_recycler.setAdapter(tuiJianAdapter);
            //为你推荐的点击事件
            tuiJianAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    //跳转的是自己的详情页面
                    Intent intent = new Intent(CartActivity.this, DetailActivity.class);
                    //传递pid
                    intent.putExtra("pid", homeBean.getTuijian().getList().get(position).getPid());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessFenLei(ResponseBody responseBody) {

    }
}
