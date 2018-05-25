package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.AddNewAddrBean;
import com.bwie.myapplication.presenter.AddNewAddrPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.iview.AddNewAddrInter;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

public class AddNewAddrAcitvity extends AppCompatActivity implements View.OnClickListener, AddNewAddrInter {

    private ImageView detail_image_back;
    /**
     * 保存
     */
    private TextView text_save;
    private RelativeLayout detai_relative;
    /**
     * 收货人
     */
    private TextView text_person;
    private EditText edit_person;
    /**
     * 联系电话
     */
    private TextView text_phone;
    private EditText edit_phone;
    /**
     * 所在地区
     */
    private TextView text_area;
    /**
     * 请选择＞
     */
    private TextView edit_area;
    private LinearLayout linear_area;
    /**
     * 街道
     */
    private TextView text_road;
    private EditText edit_road;
    private AddNewAddrPresenter addNewAddrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_addr_acitvity);
        detail_image_back = findViewById(R.id.detail_image_back);
        text_save = findViewById(R.id.text_save);
        detai_relative = findViewById(R.id.detai_relative);
        text_person = findViewById(R.id.text_person);
        edit_person = findViewById(R.id.edit_person);
        text_phone = findViewById(R.id.text_phone);
        edit_phone = findViewById(R.id.edit_phone);
        text_area = findViewById(R.id.text_area);
        edit_area = findViewById(R.id.edit_area);
        linear_area = findViewById(R.id.linear_area);
        text_road = findViewById(R.id.text_road);
        edit_road = findViewById(R.id.edit_road);
        detail_image_back.setOnClickListener(this);
        text_save.setOnClickListener(this);
        linear_area.setOnClickListener(this);//选择地址
        addNewAddrPresenter = new AddNewAddrPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                //没传值回去...此时确认订单需要finish
                finish();
                break;
            case R.id.text_save://保存
                //请求添加新地址的接口...点击保存的时候需要做一系列的非空判断
                //uid=71&addr=北京市昌平区金域国际1-1-1&mobile=18612991023&name=kson
                addNewAddrPresenter.addNewAdder(Constant.ADD_NEW_ADDR_URL, CommonUtil.getString("uid"), edit_area.getText().toString() + edit_road.getText().toString(), edit_phone.getText().toString(), edit_person.getText().toString());
                break;
            case R.id.linear_area://选择地址
                Intent intent = new Intent(AddNewAddrAcitvity.this, ChooseDistinctActivity.class);
                startActivityForResult(intent, 3001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == 3002) {
            if (data == null) {
                return;
            }
            String addr = data.getStringExtra("addr");
            edit_area.setText(addr);
        }
    }

    @Override
    public void onAddnewAddrSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            AddNewAddrBean addNewAddrBean = new Gson().fromJson(string, AddNewAddrBean.class);
            if ("0".equals(addNewAddrBean.getCode())) {//保存成功
                //请求添加成功之后...回传值给确认订单页面进行显示
                Intent intent = new Intent();
                intent.putExtra("name", edit_person.getText().toString());
                intent.putExtra("phone", edit_phone.getText().toString());
                intent.putExtra("addr", edit_area.getText().toString() + edit_road.getText().toString());
                setResult(1002, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}