package com.bwie.myapplication.presenter;

import com.bwie.myapplication.model.UpLoadPicModel;
import com.bwie.myapplication.presenter.inter.UpLoadPicPresenterInter;
import com.bwie.myapplication.view.iview.UpLoadActivityInter;

import java.io.File;

import okhttp3.ResponseBody;

public class UpLoadPicPresenter implements UpLoadPicPresenterInter {

    private UpLoadPicModel upLoadPicModel;
    private UpLoadActivityInter upLoadActivityInter;

    public UpLoadPicPresenter(UpLoadActivityInter upLoadActivityInter) {
        this.upLoadActivityInter = upLoadActivityInter;
        upLoadPicModel = new UpLoadPicModel(this);
    }

    public void uploadPic(String uploadIconUrl, File saveIconFile, String uid) {

        upLoadPicModel.uploadPic(uploadIconUrl, saveIconFile, uid);

    }

    @Override
    public void uploadPicSuccess(ResponseBody responseBody) {
        upLoadActivityInter.uploadPicSuccess(responseBody);
    }
}
