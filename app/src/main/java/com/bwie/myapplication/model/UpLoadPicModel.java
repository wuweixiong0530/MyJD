package com.bwie.myapplication.model;

import com.bwie.myapplication.presenter.inter.UpLoadPicPresenterInter;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UpLoadPicModel {

    private UpLoadPicPresenterInter upLoadPicPresenterInter;

    public UpLoadPicModel(UpLoadPicPresenterInter upLoadPicPresenterInter) {
        this.upLoadPicPresenterInter = upLoadPicPresenterInter;
    }

    //    上传图片
    public void uploadPic(String uploadIconUrl, File saveIconFile, String uid) {
        //上传的服务器的路径,,,上传的文件对象,,,上传成功之后服务器上图片的名字
        //params是存放传递的参数,,,,callback回调
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("saveIconFile", String.valueOf(saveIconFile));
        params.put("source","android");
        RetrofitUtil.getService().doGet(uploadIconUrl, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        upLoadPicPresenterInter.uploadPicSuccess(responseBody);
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
