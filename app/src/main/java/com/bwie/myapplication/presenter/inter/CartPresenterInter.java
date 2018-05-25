package com.bwie.myapplication.presenter.inter;

import okhttp3.ResponseBody;

public interface CartPresenterInter {
    void getCartDataSuccess(ResponseBody responseBody);

    void getCartDataNull();
}
