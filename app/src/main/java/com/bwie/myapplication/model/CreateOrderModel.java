package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.CreateOrderPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CreateOrderModel {
    private final CreateOrderPresenterInter createOrderPresenterInter;

    public CreateOrderModel(CreateOrderPresenterInter createOrderPresenterInter) {
        this.createOrderPresenterInter = createOrderPresenterInter;
    }

    public void createOrder(String createOrderUrl, String uid, double price) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("price", String.valueOf(price));
        RetrofitUtil.getService().doGet(createOrderUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        createOrderPresenterInter.onOrderCreateSuccess(responseBody);
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
