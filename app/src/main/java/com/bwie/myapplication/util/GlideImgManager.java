package com.bwie.myapplication.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideImgManager {
    /**
     * 加载带有placeHolder或者错误显示图片的 普通图片
     * @param context
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     */
    public static void glideLoader(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
//        Glide.with(context).load(url).apply(new RequestOptions().placeholder(emptyImg).error(erroImg)).into(iv);
    }

    /**
     * 加载带有圆角或者圆形 并且有,默认显示图片的glide
     * @param context
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     * @param tag
     */
    public static void glideLoader(Context context, String url, int erroImg, int emptyImg, ImageView iv, int tag) {
        if (0 == tag) {
//            Glide.with(context).load(url).apply(new RequestOptions().placeholder(emptyImg).error(erroImg).transform(new GlideCircleTransform(context))).into(iv);
        } else if (1 == tag) {
            Glide.with(context).load(url).apply(new RequestOptions().placeholder(emptyImg).error(erroImg).transform(new GlideRoundTransform(context,10))).into(iv);
        }
    }


}
