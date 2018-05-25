package com.bwie.myapplication.view.iview;

import okhttp3.ResponseBody;

public interface FragmentCartInter {
    void getCartDataSuccess(ResponseBody responseBody);

    void getCartDataNull();
}
