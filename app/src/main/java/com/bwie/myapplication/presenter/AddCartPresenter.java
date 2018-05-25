package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.AddCartModel;
import com.bwie.myapplication.presenter.inter.AddCartPresenterInter;
import com.bwie.myapplication.view.iview.ActivityAddCartInter;

import okhttp3.ResponseBody;

public class AddCartPresenter implements AddCartPresenterInter {

    private ActivityAddCartInter activityAddCartInter;
    private AddCartModel addCartModel;

    public AddCartPresenter(ActivityAddCartInter activityAddCartInter) {
        this.activityAddCartInter = activityAddCartInter;
        addCartModel = new AddCartModel(this);
    }

    public void addToCart(String addCart, String uid, int pid) {
        addCartModel.addToCart(addCart, uid, pid);
    }

    @Override
    public void onCartAddSuccess(ResponseBody responseBody) {
        activityAddCartInter.onCartAddSuccess(responseBody);
    }
}
