package com.bwie.myapplication.view.iview;

import okhttp3.ResponseBody;

public interface LoginActivityInter {
    void getLoginSuccess(ResponseBody responseBody);

    void getLoginSuccessByQQ(ResponseBody responseBody, String ni_cheng, String iconurl);
}
