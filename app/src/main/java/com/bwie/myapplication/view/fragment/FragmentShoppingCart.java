package com.bwie.myapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bwie.myapplication.util.ChenJinUtil;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.acitvity.DetailActivity;
import com.bwie.myapplication.view.acitvity.LoginActivity;
import com.bwie.myapplication.view.acitvity.MakeSureOrderActivity;
import com.bwie.myapplication.view.adapter.MyExpanableAdapter;
import com.bwie.myapplication.view.adapter.TuiJianAdapter;
import com.bwie.myapplication.view.custom.MyExpanableView;
import com.bwie.myapplication.view.iview.FragmentCartInter;
import com.bwie.myapplication.view.iview.InterFragmentHome;
import com.bwie.myapplication.view.iview.OnItemListner;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class FragmentShoppingCart extends Fragment implements InterFragmentHome, FragmentCartInter {
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
                text_total.setText("合计：￥"+countPriceBean.getPriceString());
                text_buy.setText("去结算（"+countPriceBean.getCount()+"）");
            }
        }
    };
    private Unbinder unbinder;
    private FragmentHomeP fragmentHomeP;
    private FragmentCartPresenter fragmentCartPresenter;
    private MyExpanableAdapter myExpanableAdapter;
    private ArrayList<CartBean.DataBean.ListBean> list_selected=new ArrayList<>();
    private CartBean cartBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChenJin();
        my_expanable_view.setGroupIndicator(null);
        tui_jian_recycler.setFocusable(false);
        fragmentHomeP = new FragmentHomeP(this);
        fragmentHomeP.getNetData(Constant.HOME_URL);
        fragmentCartPresenter = new FragmentCartPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (CommonUtil.getBoolean("isLogin")) {
            my_expanable_view.setVisibility(View.VISIBLE);
            empty_cart_image.setVisibility(View.VISIBLE);
            linear_login.setVisibility(View.GONE);
            getCartData();
        } else {
            linear_login.setVisibility(View.VISIBLE);
            my_expanable_view.setVisibility(View.GONE);
            empty_cart_image.setVisibility(View.GONE);
        }
    }

    private void getCartData() {
        relative_progress.setVisibility(View.VISIBLE);
        fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
            initChenJin();
            initData();
        }
    }

    private void initChenJin() {
        ChenJinUtil.setStatusBarColor(getActivity(),getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick({R.id.cart_login, R.id.relative_progress, R.id.cart_check_all, R.id.text_buy})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cart_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.cart_check_all:
                myExpanableAdapter.setAllChildChecked(cart_check_all.isChecked());
                break;
            case R.id.text_buy:
                if ("去结算（0）".equals(text_buy.getText().toString())){
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MakeSureOrderActivity.class);
                list_selected.clear();
                for (int i=0;i<cartBean.getData().size();i++){
                    List<CartBean.DataBean.ListBean> list = cartBean.getData().get(i).getList();
                    for (int j=0;j<list.size();j++){
                        if (list.get(j).getSelected()==1) {
                            list_selected.add(list.get(j));
                        }
                    }
                }
                intent1.putExtra("list_selected",list_selected);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessHome(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
            tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
            TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(getActivity(), homeBean.getTuijian());
            tui_jian_recycler.setAdapter(tuiJianAdapter);
            tuiJianAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("pid",homeBean.getTuijian().getList().get(position).getPid());
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

    @Override
    public void getCartDataSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            cartBean = new Gson().fromJson(string, CartBean.class);
            relative_progress.setVisibility(View.GONE);
            empty_cart_image.setVisibility(View.GONE);
            my_expanable_view.setVisibility(View.VISIBLE);
            for (int i = 0; i< cartBean.getData().size(); i++){
                CartBean.DataBean dataBean = cartBean.getData().get(i);
                dataBean.setGroupChecked(isAllChildInGroupChecked(dataBean.getList()));
            }
            cart_check_all.setChecked(isAllGroupChecked(cartBean));
            myExpanableAdapter = new MyExpanableAdapter(getActivity(), cartBean, handler, relative_progress, fragmentCartPresenter);
            my_expanable_view.setAdapter(myExpanableAdapter);
            for (int i = 0; i< cartBean.getData().size(); i++){
                my_expanable_view.expandGroup(i);
            }
            myExpanableAdapter.sendPriceAndCount();
            my_expanable_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("pid", cartBean.getData().get(groupPosition).getList().get(childPosition).getPid());
                    startActivity(intent);
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAllGroupChecked(CartBean cartBean) {
        for (int i=0;i<cartBean.getData().size();i++){
            if (!cartBean.getData().get(i).isGroupChecked()){
                return false;
            }
        }
        return true;
    }

    private boolean isAllChildInGroupChecked(List<CartBean.DataBean.ListBean> list) {
        for (int i=0;i<list.size();i++){
            if (list.get(i).getSelected()==0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void getCartDataNull() {
        relative_progress.setVisibility(View.GONE);
        empty_cart_image.setVisibility(View.VISIBLE);
        my_expanable_view.setVisibility(View.GONE);
        Toast.makeText(getActivity(),"购物车为空，快去狂狂",Toast.LENGTH_SHORT).show();
    }
}