package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.RegistPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RegistModel {
    private final RegistPresenterInter registPresenterInter;

    public RegistModel(RegistPresenterInter registPresenterInter) {
        this.registPresenterInter = registPresenterInter;
    }

    public void registUser(String registUrl, String name, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", name);
        params.put("password", pwd);
        RetrofitUtil.getService().doGet(registUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        registPresenterInter.onSuccess(responseBody);
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
