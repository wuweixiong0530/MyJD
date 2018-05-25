package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.CartPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CartModel {
    private final CartPresenterInter cartPresenterInter;

    public CartModel(CartPresenterInter cartPresenterInter) {
        this.cartPresenterInter = cartPresenterInter;
    }

    public void getCartData(String selectCart, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        RetrofitUtil.getService().doGet(selectCart, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        cartPresenterInter.getCartDataSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        cartPresenterInter.getCartDataNull();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
