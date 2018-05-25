package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.CreateOrderModel;
import com.bwie.myapplication.presenter.inter.CreateOrderPresenterInter;
import com.bwie.myapplication.view.iview.CreateOrderInter;

import okhttp3.ResponseBody;

public class CreateOrderPresenter implements CreateOrderPresenterInter {

    private final CreateOrderModel createOrderModel;
    private final CreateOrderInter createOrderInter;

    public CreateOrderPresenter(CreateOrderInter createOrderInter) {
        this.createOrderInter = createOrderInter;
        createOrderModel = new CreateOrderModel(this);
    }

    public void createOrder(String createOrderUrl, String uid, double price) {
        createOrderModel.createOrder(createOrderUrl, uid, price);
    }

    @Override
    public void onOrderCreateSuccess(ResponseBody responseBody) {
        createOrderInter.onCreateOrderSuccess(responseBody);
    }
}
