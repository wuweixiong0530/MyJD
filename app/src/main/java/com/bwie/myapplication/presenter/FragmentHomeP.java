package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.FragmentHomeModel;
import com.bwie.myapplication.presenter.inter.FragmentHomePInter;
import com.bwie.myapplication.view.iview.InterFragmentHome;

import okhttp3.ResponseBody;

public class FragmentHomeP implements FragmentHomePInter {
    private final InterFragmentHome interFragmentHome;
    private final FragmentHomeModel fragmentHomeModel;
    //创建构造方法
    public FragmentHomeP(InterFragmentHome interFragmentHome) {
        this.interFragmentHome = interFragmentHome;
        //创建model的引用
        fragmentHomeModel = new FragmentHomeModel(this);
    }

    public void getNetData(String homeUrl) {
        //让model获取数据
        fragmentHomeModel.getData(homeUrl);
    }

    public void getFenLeiData(String fenLeiUrl) {
        fragmentHomeModel.getFenLeiData(fenLeiUrl);
    }

    @Override
    public void onSuccessHome(ResponseBody responseBody) {
        //此时的数据回调到p层,,,把数据从p层传到view层进行使用
        interFragmentHome.onSuccessHome(responseBody);
    }

    @Override
    public void onSuccessFenLei(ResponseBody responseBody) {
        interFragmentHome.onSuccessFenLei(responseBody);
    }
}
