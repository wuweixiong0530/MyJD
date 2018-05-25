package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.DeatilModel;
import com.bwie.myapplication.presenter.inter.DeatilPresenterInter;
import com.bwie.myapplication.view.iview.DetailActivityInter;

import okhttp3.ResponseBody;

public class DeatailPresenter implements DeatilPresenterInter {
    private DeatilModel deatilModel;
    private DetailActivityInter detailActivityInter;

    public DeatailPresenter(DetailActivityInter detailActivityInter) {
        this.detailActivityInter = detailActivityInter;
        deatilModel = new DeatilModel(this);
    }

    public void getDetailData(String detailUrl, int pid) {
        deatilModel.getDetailData(detailUrl, pid);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        //回调给view
        detailActivityInter.onSuccess(responseBody);
    }
}
