package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.ProductListModel;
import com.bwie.myapplication.presenter.inter.ProductListPresenterInter;
import com.bwie.myapplication.view.iview.ProductListActivityInter;

import okhttp3.ResponseBody;

public class ProductListPresenter implements ProductListPresenterInter {
    private final ProductListActivityInter productListActivityInter;
    private final ProductListModel productListModel;

    public ProductListPresenter(ProductListActivityInter productListActivityInter) {
        this.productListActivityInter=productListActivityInter;
        productListModel = new ProductListModel(this);
    }
    public void getProductData(String seartchUrl,String keywords,int page){
        productListModel.getProductData(seartchUrl,keywords,page);
    }
    @Override
    public void onSuccess(ResponseBody responseBody) {
        productListActivityInter.getProductDataSuccess(responseBody);
    }
}
