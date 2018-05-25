package com.bwie.myapplication.view.acitvity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.RegistBean;
import com.bwie.myapplication.presenter.RegistPresenter;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.iview.RegistActivityInter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener, RegistActivityInter {

    private ImageView cha_image;
    private RelativeLayout login_title_relative;
    /**
     * 请输入手机号
     */
    private EditText edit_phone;
    /**
     * 请输入密码
     */
    private EditText edit_pwd;
    private RegistPresenter registPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        cha_image = (ImageView) findViewById(R.id.cha_image);
        login_title_relative = (RelativeLayout) findViewById(R.id.login_title_relative);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        registPresenter = new RegistPresenter(this);
        cha_image.setOnClickListener(this);
    }

    public void regist(View view) {
        String name = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();
        registPresenter.registUser(Constant.REGIST_URL, name, pwd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cha_image:
                finish();
                break;
        }
    }

    @Override
    public void onRegistSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            RegistBean registBean = new Gson().fromJson(string, RegistBean.class);
            String code = registBean.getCode();
            if ("1".equals(code)) {
                Toast.makeText(RegistActivity.this, registBean.getMsg(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistActivity.this, registBean.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
