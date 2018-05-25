package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.FragmentHomePInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FragmentHomeModel {
    private final FragmentHomePInter fragmentHomePInter;

    //接收p的时候需要使用接口形式进行接收....
    public FragmentHomeModel(FragmentHomePInter fragmentHomePInter) {
        this.fragmentHomePInter = fragmentHomePInter;
    }

    public void getData(String homeUrl) {
        Map<String, String> params = new HashMap<>();
        RetrofitUtil.getService().doGet(homeUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //在这里进行数据的回调
                        fragmentHomePInter.onSuccessHome(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getFenLeiData(String feiLeiUrl) {
        Map<String, String> params = new HashMap<>();
        RetrofitUtil.getService().doGet(feiLeiUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        fragmentHomePInter.onSuccessFenLei(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
