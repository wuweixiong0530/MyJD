package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.OrderListModel;
import com.bwie.myapplication.presenter.inter.OrderListPresenterInter;
import com.bwie.myapplication.view.iview.FragmentOrderListInter;

import okhttp3.ResponseBody;

public class OrderListPresenter implements OrderListPresenterInter {

    private FragmentOrderListInter fragmentOrderListInter;
    private OrderListModel orderListModel;

    public OrderListPresenter(FragmentOrderListInter fragmentOrderListInter) {
        this.fragmentOrderListInter = fragmentOrderListInter;
        orderListModel = new OrderListModel(this);
    }

    public void getOrderData(String orderListUrl, String uid, int page) {

        orderListModel.getOrderData(orderListUrl, uid, page);

    }

    @Override
    public void onOrderDataSuccess(ResponseBody responseBody) {
        fragmentOrderListInter.onOrderDataSuccess(responseBody);
    }
}
