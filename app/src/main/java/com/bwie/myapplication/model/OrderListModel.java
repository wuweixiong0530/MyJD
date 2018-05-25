package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.OrderListPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OrderListModel {
    private OrderListPresenterInter orderListPresenterInter;

    public OrderListModel(OrderListPresenterInter orderListPresenterInter) {
        this.orderListPresenterInter = orderListPresenterInter;
    }

    public void getOrderData(String orderListUrl, String uid, int page) {

        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("page", String.valueOf(page));
        RetrofitUtil.getService().doGet(orderListUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        orderListPresenterInter.onOrderDataSuccess(responseBody);
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
