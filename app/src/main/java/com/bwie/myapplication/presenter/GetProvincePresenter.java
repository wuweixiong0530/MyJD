package com.bwie.myapplication.presenter;

import android.content.Context;

import com.bwie.myapplication.model.GetProvinceModel;
import com.bwie.myapplication.model.bean.ProvinceBean;
import com.bwie.myapplication.presenter.inter.GetProvincePresenterInter;
import com.bwie.myapplication.view.iview.GetProvinceInter;

import java.util.List;

public class GetProvincePresenter implements GetProvincePresenterInter {
    private final GetProvinceInter getProvinceInter;
    private final GetProvinceModel getProvinceModel;

    public GetProvincePresenter(GetProvinceInter getProvinceInter) {
        this.getProvinceInter = getProvinceInter;
        getProvinceModel = new GetProvinceModel(this);
    }

    public void getProvince(Context context) {
        getProvinceModel.getProvince(context);
    }

    @Override
    public void onGetProvince(List<ProvinceBean> list) {
        getProvinceInter.onGetProvince(list);
    }
}
