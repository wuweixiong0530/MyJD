package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.GetAllAddrModel;
import com.bwie.myapplication.presenter.inter.GetAllAddrPresenterInter;
import com.bwie.myapplication.view.iview.GetAllAddrInter;

import okhttp3.ResponseBody;

public class GetAllAddrPresenter implements GetAllAddrPresenterInter {
    private final GetAllAddrInter getAllAddrInter;
    private final GetAllAddrModel getAllAddrModel;

    public GetAllAddrPresenter(GetAllAddrInter getAllAddrInter) {
        this.getAllAddrInter = getAllAddrInter;
        getAllAddrModel = new GetAllAddrModel(this);
    }

    public void getGetAllAddr(String getAllAddrUrl, String uid) {
        getAllAddrModel.getAllAddr(getAllAddrUrl, uid);
    }

    @Override
    public void onGetAllAddrSuccess(ResponseBody responseBody) {
        getAllAddrInter.onGetAllAddrSuccess(responseBody);
    }
}
