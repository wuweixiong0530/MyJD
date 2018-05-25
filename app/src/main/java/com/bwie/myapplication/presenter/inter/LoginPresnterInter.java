package com.bwie.myapplication.presenter.inter;

import okhttp3.ResponseBody;

public interface LoginPresnterInter {
    void onSuccess(ResponseBody responseBody);

    void onSuccessByQQ(ResponseBody responseBody, String ni_cheng, String iconurl);
}
