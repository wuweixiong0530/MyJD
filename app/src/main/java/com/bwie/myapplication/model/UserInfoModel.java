package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.UserInfoPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UserInfoModel {

    private UserInfoPresenterInter userInfoPresenterInter;

    public UserInfoModel(UserInfoPresenterInter userInfoPresenterInter) {
        this.userInfoPresenterInter = userInfoPresenterInter;
    }

    public void getUserInfo(String userInfoUrl, String uid) {

        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("source","android");
        RetrofitUtil.getService().doGet(userInfoUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        userInfoPresenterInter.onUserInfoSUccess(responseBody);
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
