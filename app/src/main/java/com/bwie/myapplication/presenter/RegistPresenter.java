package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.RegistModel;
import com.bwie.myapplication.presenter.inter.RegistPresenterInter;
import com.bwie.myapplication.view.iview.RegistActivityInter;

import okhttp3.ResponseBody;

public class RegistPresenter implements RegistPresenterInter {

    private final RegistActivityInter registActivityInter;
    private final RegistModel registModel;

    public RegistPresenter(RegistActivityInter registActivityInter) {
        this.registActivityInter = registActivityInter;
        registModel = new RegistModel(this);
    }

    public void registUser(String registUrl, String name, String pwd) {
        registModel.registUser(registUrl, name, pwd);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        registActivityInter.onRegistSuccess(responseBody);
    }
}
