package com.bwie.myapplication.presenter.inter;

import okhttp3.ResponseBody;

public interface CreateOrderPresenterInter {
    void onOrderCreateSuccess(ResponseBody responseBody);
}
