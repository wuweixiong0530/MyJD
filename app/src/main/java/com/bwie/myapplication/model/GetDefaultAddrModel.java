package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.GetDefaultAddrPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class GetDefaultAddrModel {
    private final GetDefaultAddrPresenterInter getDefaultAddrPresenterInter;

    public GetDefaultAddrModel(GetDefaultAddrPresenterInter getDefaultAddrPresenterInter) {
        this.getDefaultAddrPresenterInter = getDefaultAddrPresenterInter;
    }

    public void getDefaultAddr(String getDefaultAddrUrl, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        RetrofitUtil.getService().doGet(getDefaultAddrUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getDefaultAddrPresenterInter.onGetDefaultAddrSuuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getDefaultAddrPresenterInter.onGetDefaultAddrEmpty();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
