package com.bwie.myapplication.view.acitvity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.view.fragment.FragmentAllOrder;
import com.bwie.myapplication.view.fragment.FragmentAlreadyPayOrder;
import com.bwie.myapplication.view.fragment.FragmentCacelOrder;
import com.bwie.myapplication.view.fragment.FragmentDaiPayOrder;

public class OrderListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView detail_image_back;
    private ImageView detail_share;
    private RadioGroup radio_group;
    private ImageView san_dian_pop;
    private PopupWindow popupWindow;
    private TextView pop_dai_pay;
    private TextView pop_already_pay;
    private TextView pop_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        //找到控件
        detail_image_back = findViewById(R.id.detail_image_back);
        radio_group = findViewById(R.id.radio_group);
        san_dian_pop = findViewById(R.id.san_dian_pop);

        detail_image_back.setOnClickListener(this);
        san_dian_pop.setOnClickListener(this);

        initPopUpWindown();

        int flag = getIntent().getIntExtra("flag", -1);

        //这个订单列表页面可以从提交订单跳过来,,,,还可以从我的那个fragment去跳转
        //默认显示的是全部页面
        if (flag == -1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentAllOrder()).commit();
        } else {
            //如果从fragemnt跳转过来 需要展示自己的页面
            if (flag == 1) {//待支付
                radio_group.check(R.id.radio_02);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentDaiPayOrder()).commit();
            } else if (flag == 2) {//已支付
                radio_group.check(R.id.radio_03);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentAlreadyPayOrder()).commit();
            } else if (flag == 3) {//已取消
                radio_group.check(R.id.radio_04);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentCacelOrder()).commit();
            }
        }


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radio_01://全部
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentAllOrder()).commit();
                        break;
                    case R.id.radio_02://待支付
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentDaiPayOrder()).commit();
                        break;
                    case R.id.radio_03://已支付
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentAlreadyPayOrder()).commit();
                        break;
                    case R.id.radio_04://已取消
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentCacelOrder()).commit();
                        break;
                }
            }
        });
    }

    private void initPopUpWindown() {

        View view = View.inflate(OrderListActivity.this, R.layout.order_pop_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //找到控件
        pop_dai_pay = view.findViewById(R.id.pop_dai_pay);
        pop_already_pay = view.findViewById(R.id.pop_already_pay);
        pop_cancel = view.findViewById(R.id.pop_cancel);

        pop_dai_pay.setOnClickListener(this);
        pop_already_pay.setOnClickListener(this);
        pop_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.san_dian_pop://弹出pop
                //判断一下当前radioGroup选中了哪一个RadioButton...设置展示的背景颜色
                int checkedRadioButtonId = radio_group.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.radio_02://待支付
                        pop_dai_pay.setBackgroundColor(Color.BLUE);
                        pop_already_pay.setBackgroundColor(Color.WHITE);
                        pop_cancel.setBackgroundColor(Color.WHITE);
                        break;
                    case R.id.radio_03://已支付
                        pop_dai_pay.setBackgroundColor(Color.WHITE);
                        pop_already_pay.setBackgroundColor(Color.BLUE);
                        pop_cancel.setBackgroundColor(Color.WHITE);
                        break;
                    case R.id.radio_04://已取消
                        pop_dai_pay.setBackgroundColor(Color.WHITE);
                        pop_already_pay.setBackgroundColor(Color.WHITE);
                        pop_cancel.setBackgroundColor(Color.BLUE);
                        break;
                }

                popupWindow.showAsDropDown(san_dian_pop);

                break;
            case R.id.pop_dai_pay://待支付
                radio_group.check(R.id.radio_02);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentDaiPayOrder()).commit();
                popupWindow.dismiss();
                break;
            case R.id.pop_already_pay://已支付
                radio_group.check(R.id.radio_03);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentAlreadyPayOrder()).commit();
                popupWindow.dismiss();
                break;
            case R.id.pop_cancel://已取消
                radio_group.check(R.id.radio_04);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentCacelOrder()).commit();
                popupWindow.dismiss();
                break;
        }
    }
}
