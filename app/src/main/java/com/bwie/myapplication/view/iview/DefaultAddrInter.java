package com.bwie.myapplication.view.iview;

import okhttp3.ResponseBody;

public interface DefaultAddrInter {
    void onGetDefultAddrSuccess(ResponseBody responseBody);

    void onGetDefaultAddrEmpty();
}
