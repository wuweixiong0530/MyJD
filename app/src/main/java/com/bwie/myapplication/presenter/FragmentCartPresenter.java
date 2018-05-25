package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.CartModel;
import com.bwie.myapplication.presenter.inter.CartPresenterInter;
import com.bwie.myapplication.view.iview.FragmentCartInter;

import okhttp3.ResponseBody;

public class FragmentCartPresenter implements CartPresenterInter {
    private final FragmentCartInter fragmentCartInter;
    private final CartModel cartModel;

    public FragmentCartPresenter(FragmentCartInter fragmentCartInter) {
        this.fragmentCartInter = fragmentCartInter;
        cartModel = new CartModel(this);
    }

    public void getCartData(String selectCart, String uid) {
        cartModel.getCartData(selectCart, uid);
    }

    @Override
    public void getCartDataSuccess(ResponseBody responseBody) {
        fragmentCartInter.getCartDataSuccess(responseBody);
    }

    @Override
    public void getCartDataNull() {
        fragmentCartInter.getCartDataNull();
    }

}
