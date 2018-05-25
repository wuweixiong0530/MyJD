package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.LoginBean;
import com.bwie.myapplication.presenter.LoginPresnter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.iview.LoginActivityInter;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity implements LoginActivityInter, View.OnClickListener {
    @BindView(R.id.login_title_relative)
    RelativeLayout login_title_relative;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;
    @BindView(R.id.text_regist)
    TextView text_regist;
    @BindView(R.id.login_by_wechat)
    ImageView login_by_wechat;
    @BindView(R.id.login_by_qq)
    ImageView login_by_qq;
    private LoginPresnter loginPresnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //登录的presenter
        loginPresnter = new LoginPresnter(this);
    }
//登录的点击事件
    public void login(View view) {
        String phone = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();
        loginPresnter.getLogin(Constant.LOGIN_URL, phone, pwd);
    }

    @Override
    public void getLoginSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            LoginBean loginBean = new Gson().fromJson(string, LoginBean.class);
            Toast.makeText(this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
            if ("0".equals(loginBean.getCode())) {//登录成功
                //登录成功之后需要做:.....保存状态true...
                CommonUtil.putBoolean("isLogin", true);
                CommonUtil.saveString("uid", String.valueOf(loginBean.getData().getUid()));
                CommonUtil.saveString("name", loginBean.getData().getUsername());
                CommonUtil.saveString("iconUrl", loginBean.getData().getIcon());
                //结束当前界面
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLoginSuccessByQQ(ResponseBody responseBody, String ni_cheng, String iconurl) {
        try {
            String string = responseBody.string();
            LoginBean loginBean = new Gson().fromJson(string, LoginBean.class);
            if ("0".equals(loginBean.getCode())) {
                CommonUtil.putBoolean("isLogin", true);
                CommonUtil.saveString("uid", String.valueOf(loginBean.getData().getUid()));
                CommonUtil.saveString("name", ni_cheng);
                CommonUtil.saveString("iconUrl", iconurl);
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
            String qq_uid = data.get("uid");
            String ni_cheng = data.get("name");
            String iconurl = data.get("iconurl");
            loginPresnter.getLoginByQQ("18500462136", "112245", ni_cheng, iconurl);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable t) {
            Toast.makeText(LoginActivity.this, "失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void submit() {
        String phone = edit_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = edit_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @OnClick({R.id.text_regist, R.id.login_by_wechat, R.id.login_by_qq})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.text_regist:
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.login_by_wechat:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.login_by_qq:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
        }
    }
}
