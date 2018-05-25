package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.LoginPresnterInter;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginModel {

    private final LoginPresnterInter loginPresnterInter;

    public LoginModel(LoginPresnterInter loginActivityInter) {
        this.loginPresnterInter = loginActivityInter;
    }

    public void getLogin(String loginUrl, String phone, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", pwd);
        RetrofitUtil.getService().doGet(loginUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loginPresnterInter.onSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

        public void getLoginByQQ(String phone, String pwd, final String ni_cheng, final String iconurl) {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", phone);
            params.put("password", pwd);
            RetrofitUtil.getService().doGet(Constant.LOGIN_URL, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            loginPresnterInter.onSuccessByQQ(responseBody, ni_cheng, iconurl);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
//            OkHttpUtil.doPost(ApiUtil.LOGIN_URL, params, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        String json = response.body().string();
//                        final LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);
//                        CommonUtil.runOnUIThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                    }
//                }
//            });
        }
}
