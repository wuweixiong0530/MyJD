package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.ProductListPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ProductListModel {
    private final ProductListPresenterInter productListPresenterInter;

    public ProductListModel(ProductListPresenterInter productListPresenterInter) {
        this.productListPresenterInter=productListPresenterInter;
    }
    public void getProductData(String seartchUrl,String keywords,int page){
        Map<String,String> params=new HashMap<>();
        params.put("keywords",keywords);
        params.put("page", String.valueOf(page));
        RetrofitUtil.getService().doGet(seartchUrl,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        productListPresenterInter.onSuccess(responseBody);
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
