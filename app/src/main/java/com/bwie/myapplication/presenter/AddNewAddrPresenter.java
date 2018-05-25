package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.AddNewAddrModel;
import com.bwie.myapplication.presenter.inter.AddNewAddrPresenterInter;
import com.bwie.myapplication.view.iview.AddNewAddrInter;

import okhttp3.ResponseBody;

public class AddNewAddrPresenter implements AddNewAddrPresenterInter {

    private final AddNewAddrModel addNewAddrModel;
    private final AddNewAddrInter addNewAddrInter;

    public AddNewAddrPresenter(AddNewAddrInter addNewAddrInter) {
        this.addNewAddrInter = addNewAddrInter;
        addNewAddrModel = new AddNewAddrModel(this);
    }

    public void addNewAdder(String addNewAddrUrl, String uid, String addr, String phone, String name) {
        addNewAddrModel.addNewAddr(addNewAddrUrl, uid, addr, phone, name);
    }

    @Override
    public void onAddAddrSuccess(ResponseBody responseBody) {
        addNewAddrInter.onAddnewAddrSuccess(responseBody);
    }
}
