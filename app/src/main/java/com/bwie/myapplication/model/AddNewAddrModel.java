package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.AddNewAddrPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class AddNewAddrModel {
    private final AddNewAddrPresenterInter addNewAddrPresenterInter;

    public AddNewAddrModel(AddNewAddrPresenterInter addNewAddrPresenterInter) {
        this.addNewAddrPresenterInter = addNewAddrPresenterInter;
    }

    public void addNewAddr(String addNewAddrUrl, String uid, final String addr, String phone, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("addr", addr);
        params.put("mobile", phone);
        params.put("name", name);
        RetrofitUtil.getService().doGet(addNewAddrUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        addNewAddrPresenterInter.onAddAddrSuccess(responseBody);
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
