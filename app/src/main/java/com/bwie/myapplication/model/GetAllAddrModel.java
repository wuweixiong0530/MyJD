package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.GetAllAddrPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class GetAllAddrModel {

    private final GetAllAddrPresenterInter getAllAddrPresenterInter;

    public GetAllAddrModel(GetAllAddrPresenterInter getAllAddrPresenterInter) {
        this.getAllAddrPresenterInter = getAllAddrPresenterInter;
    }

    public void getAllAddr(String getAllAddrUrl, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        RetrofitUtil.getService().doGet(getAllAddrUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getAllAddrPresenterInter.onGetAllAddrSuccess(responseBody);
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
