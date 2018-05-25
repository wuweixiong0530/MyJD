package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.LoginModel;
import com.bwie.myapplication.presenter.inter.LoginPresnterInter;
import com.bwie.myapplication.view.iview.LoginActivityInter;

import okhttp3.ResponseBody;

public class LoginPresnter implements LoginPresnterInter {

    private final LoginActivityInter loginActivityInter;
    private final LoginModel loginModel;

    public LoginPresnter(LoginActivityInter loginActivityInter) {
        this.loginActivityInter = loginActivityInter;
        loginModel = new LoginModel(this);
    }
    //处理逻辑用的...判断
    public void getLogin(String loginUrl, String phone, String pwd) {
        loginModel.getLogin(loginUrl, phone, pwd);
    }

    public void getLoginByQQ(String phone, String pwd, String ni_cheng, String iconurl) {
        loginModel.getLoginByQQ(phone, pwd, ni_cheng, iconurl);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        loginActivityInter.getLoginSuccess(responseBody);
    }

    @Override
    public void onSuccessByQQ(ResponseBody responseBody, String ni_cheng, String iconurl) {
        loginActivityInter.getLoginSuccessByQQ(responseBody, ni_cheng, iconurl);
    }


}
