package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.UserInfoModel;
import com.bwie.myapplication.presenter.inter.UserInfoPresenterInter;
import com.bwie.myapplication.view.iview.UserInforInter;

import okhttp3.ResponseBody;

public class UserInfoPresenter implements UserInfoPresenterInter {

    private final UserInfoModel userInfoModel;
    private final UserInforInter userInforInter;

    public UserInfoPresenter(UserInforInter userInforInter) {
        this.userInforInter = userInforInter;
        userInfoModel = new UserInfoModel(this);
    }

    public void getUserInfo(String userInfoUrl, String uid) {

        userInfoModel.getUserInfo(userInfoUrl, uid);

    }

    @Override
    public void onUserInfoSUccess(ResponseBody responseBody) {
        userInforInter.onUserInforSuccess(responseBody);
    }
}
