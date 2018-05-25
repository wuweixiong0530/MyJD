package com.bwie.myapplication.view.acitvity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.CartBean;
import com.bwie.myapplication.model.bean.CreateOrderBean;
import com.bwie.myapplication.model.bean.DefaultAddrBean;
import com.bwie.myapplication.model.bean.GetAllAddrBean;
import com.bwie.myapplication.presenter.CreateOrderPresenter;
import com.bwie.myapplication.presenter.GetDefaultAddrPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.RetrofitUtil;
import com.bwie.myapplication.view.adapter.SureOrderAdapter;
import com.bwie.myapplication.view.iview.CreateOrderInter;
import com.bwie.myapplication.view.iview.DefaultAddrInter;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MakeSureOrderActivity extends AppCompatActivity implements DefaultAddrInter, CreateOrderInter, View.OnClickListener {

    private ImageView detail_image_back;
    private RelativeLayout detai_relative;
    /**
     * 收货人：
     */
    private TextView text_name;
    private TextView text_phone;
    private ImageView ding_wei_image;
    private TextView text_addr;
    private RelativeLayout recycler_addr_01;
    private RecyclerView product_list_recycler;
    /**
     * 实付款：￥0.00
     */
    private TextView text_shi_fu_ku;
    /**
     * 提交订单
     */
    private TextView text_submit_order;
    private LinearLayout linear_bottom;
    private ArrayList<CartBean.DataBean.ListBean> list_selected;
    private GetDefaultAddrPresenter getDefaultAddrPresenter;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private CreateOrderPresenter createOrderPresenter;
    private double price;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sure_order);
        detail_image_back = findViewById(R.id.detail_image_back);
        detai_relative = findViewById(R.id.detai_relative);
        text_name = findViewById(R.id.text_name);
        text_phone = findViewById(R.id.text_phone);
        ding_wei_image = findViewById(R.id.ding_wei_image);
        text_addr = findViewById(R.id.text_addr);
        recycler_addr_01 = findViewById(R.id.recycler_addr_01);
        product_list_recycler = findViewById(R.id.product_list_recycler);
        text_shi_fu_ku = findViewById(R.id.text_shi_fu_ku);
        text_submit_order = findViewById(R.id.text_submit_order);
        linear_bottom = findViewById(R.id.linear_bottom);
        list_selected = (ArrayList<CartBean.DataBean.ListBean>) getIntent().getSerializableExtra("list_selected");
        getDefaultAddrPresenter = new GetDefaultAddrPresenter(this);
        getDefaultAddrPresenter.getDefaultAddr(Constant.GET_DEFAULT_ADDR_URL, CommonUtil.getString("uid"));
    }

    private void initData() {
        createOrderPresenter = new CreateOrderPresenter(this);
        detail_image_back.setOnClickListener(this);
        text_submit_order.setOnClickListener(this);
        recycler_addr_01.setOnClickListener(this);
        product_list_recycler.setLayoutManager(new LinearLayoutManager(MakeSureOrderActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MakeSureOrderActivity.this, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_decoration_shape));
        product_list_recycler.addItemDecoration(dividerItemDecoration);
        SureOrderAdapter sureOrderAdapter = new SureOrderAdapter(MakeSureOrderActivity.this, list_selected);
        product_list_recycler.setAdapter(sureOrderAdapter);
        price = 0;
        for (int i = 0; i < list_selected.size(); i++) {
            price += list_selected.get(i).getBargainPrice() * list_selected.get(i).getNum();
        }
        String priceString = decimalFormat.format(price);
        text_shi_fu_ku.setText("实付款" + price);
    }

    @Override
    public void onGetDefultAddrSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            DefaultAddrBean defaultAddrBean = new Gson().fromJson(string, DefaultAddrBean.class);
            text_name.setText("收货人：" + defaultAddrBean.getData().getName());
            text_phone.setText(defaultAddrBean.getData().getMobile() + "");
            text_addr.setText("收货地址:" + defaultAddrBean.getData().getAddr());
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetDefaultAddrEmpty() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeSureOrderActivity.this);
        builder.setTitle("提示")
                .setMessage("您还没有默认的收货地址，请设置收货地址")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MakeSureOrderActivity.this.finish();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MakeSureOrderActivity.this, AddNewAddrAcitvity.class);
                        startActivityForResult(intent, 1001);
                    }
                })
                .show();
    }


    private void deleteProductInCart(final ArrayList<CartBean.DataBean.ListBean> list_selected) {
        CartBean.DataBean.ListBean listBean = list_selected.get(index);
        Map<String, String> params = new HashMap<>();
        params.put("uid", CommonUtil.getString("uid"));
        params.put("pid", String.valueOf(listBean.getPid()));
        RetrofitUtil.getService().doGet(Constant.DELETE_CART_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        index++;
                        if (index < list_selected.size()) {
                            deleteProductInCart(list_selected);
                        } else {
                            Toast.makeText(MakeSureOrderActivity.this, "应该调用支付的操作，然后再跳转订单列表", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MakeSureOrderActivity.this,OrderListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.text_submit_order:
                createOrderPresenter.createOrder(Constant.CREATE_ORDER_URL, CommonUtil.getString("uid"), price);
                break;
            case R.id.recycler_addr_01:
                Intent intent = new Intent(MakeSureOrderActivity.this, ChooseAddrActivity.class);
                startActivityForResult(intent, 2001);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            if (data == null) {
                finish();
                return;
            }
            text_name.setText("收货人：" + data.getStringExtra("name"));
            text_phone.setText(data.getStringExtra("phone"));
            text_addr.setText("收货地址：" + data.getStringExtra("addr"));
            initData();
        }
        if (requestCode == 2001 && resultCode == 2002) {
            if (data == null) {
                return;
            }
            GetAllAddrBean.DataBean dataBean = (GetAllAddrBean.DataBean) data.getSerializableExtra("addrBean");
            text_name.setText("收货人：" + dataBean.getName());
            text_phone.setText(String.valueOf(dataBean.getMobile()));
            text_addr.setText("收货地址：" + dataBean.getAddr());
        }
    }

    @Override
    public void onCreateOrderSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            CreateOrderBean createOrderBean = new Gson().fromJson(string, CreateOrderBean.class);
            if ("0".equals(createOrderBean.getCode())) {
                index = 0;
                deleteProductInCart(list_selected);
            } else {
                Toast.makeText(MakeSureOrderActivity.this, createOrderBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
