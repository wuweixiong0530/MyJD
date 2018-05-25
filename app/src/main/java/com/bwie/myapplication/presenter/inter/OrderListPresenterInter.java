package com.bwie.myapplication.presenter.inter;

import okhttp3.ResponseBody;

public interface OrderListPresenterInter {
    void onOrderDataSuccess(ResponseBody responseBody);
}
