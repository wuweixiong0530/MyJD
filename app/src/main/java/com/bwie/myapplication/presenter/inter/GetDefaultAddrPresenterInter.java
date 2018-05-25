package com.bwie.myapplication.presenter.inter;

import okhttp3.ResponseBody;

public interface GetDefaultAddrPresenterInter {
    void onGetDefaultAddrSuuccess(ResponseBody responseBody);

    void onGetDefaultAddrEmpty();
}
