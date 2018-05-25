package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.FragmentFenLeiRightModel;
import com.bwie.myapplication.presenter.inter.FenLeiRightPresenterInter;
import com.bwie.myapplication.view.iview.FenLeiRightInter;

import okhttp3.ResponseBody;

public class FragmentFenLeiRightPresenter implements FenLeiRightPresenterInter {

    private final FenLeiRightInter fenLeiRightInter;
    private final FragmentFenLeiRightModel fragmentFenLeiRightModel;

    public FragmentFenLeiRightPresenter(FenLeiRightInter fenLeiRightInter) {
        this.fenLeiRightInter = fenLeiRightInter;
        fragmentFenLeiRightModel = new FragmentFenLeiRightModel(this);
    }

    public void getChildData(String childFenLeiUrl, int cid) {
        fragmentFenLeiRightModel.getChildData(childFenLeiUrl, cid);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        fenLeiRightInter.onSuccessChildData(responseBody);
    }
}