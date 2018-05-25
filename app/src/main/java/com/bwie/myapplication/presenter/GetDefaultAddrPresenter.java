package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.GetDefaultAddrModel;
import com.bwie.myapplication.presenter.inter.GetDefaultAddrPresenterInter;
import com.bwie.myapplication.view.iview.DefaultAddrInter;

import okhttp3.ResponseBody;

public class GetDefaultAddrPresenter implements GetDefaultAddrPresenterInter {
    private final DefaultAddrInter defaultAddrInter;
    private final GetDefaultAddrModel getDefaultAddrModel;

    public GetDefaultAddrPresenter(DefaultAddrInter defaultAddrInter) {
        this.defaultAddrInter = defaultAddrInter;
        getDefaultAddrModel = new GetDefaultAddrModel(this);
    }

    public void getDefaultAddr(String getDefaultAddrUrl, String uid) {
        getDefaultAddrModel.getDefaultAddr(getDefaultAddrUrl, uid);
    }

    @Override
    public void onGetDefaultAddrSuuccess(ResponseBody responseBody) {
        defaultAddrInter.onGetDefultAddrSuccess(responseBody);
    }

    @Override
    public void onGetDefaultAddrEmpty() {
        defaultAddrInter.onGetDefaultAddrEmpty();
    }

}
