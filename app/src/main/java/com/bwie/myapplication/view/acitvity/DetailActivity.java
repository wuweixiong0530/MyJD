package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.AddCartBean;
import com.bwie.myapplication.model.bean.DeatilBean;
import com.bwie.myapplication.model.bean.MessageBean;
import com.bwie.myapplication.presenter.AddCartPresenter;
import com.bwie.myapplication.presenter.DeatailPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.GlideImageLoader;
import com.bwie.myapplication.utils.ShareUtil;
import com.bwie.myapplication.view.iview.ActivityAddCartInter;
import com.bwie.myapplication.view.iview.DetailActivityInter;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 详情页
 */
public class DetailActivity extends AppCompatActivity implements DetailActivityInter, View.OnClickListener, ActivityAddCartInter {

    @BindView(R.id.detail_image_back)
    ImageView detail_image_back;
    @BindView(R.id.detail_share)
    ImageView detail_share;
    @BindView(R.id.detai_relative)
    RelativeLayout detai_relative;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.detail_title)
    TextView detail_title;
    @BindView(R.id.detail_yuan_price)
    TextView detail_yuan_price;
    @BindView(R.id.detail_bargin_price)
    TextView detail_bargin_price;
    @BindView(R.id.watch_cart)
    TextView watch_cart;
    @BindView(R.id.detai_add_cart)
    TextView detai_add_cart;

    private int pid;
    private DeatailPresenter deatailPresenter;
    private AddCartPresenter addCartPresenter;
    private DeatilBean deatilBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        //创建presenter
        deatailPresenter = new DeatailPresenter(this);
        addCartPresenter = new AddCartPresenter(this);

        //如果不是默认值代表传递过来数据了
        if (pid != -1) {
            //拿着传递的pid请求商品详情的接口,然后展示数据...MVP
            deatailPresenter.getDetailData(Constant.DETAIL_URL, pid);
        }
        //初始化banner
        initBanner();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getMessage(MessageBean messageBean) {
        pid = Integer.parseInt(messageBean.getId());
    }

    private void initBanner() {
        //设置banner样式...CIRCLE_INDICATOR_TITLE包含标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            deatilBean = new Gson().fromJson(string, DeatilBean.class);
            DeatilBean.DataBean data = deatilBean.getData();
            if (data != null) {
                //添加删除线
                detail_yuan_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //设置数据显示
                detail_title.setText(data.getTitle());
                detail_bargin_price.setText("优惠价:" + data.getBargainPrice());
                detail_yuan_price.setText("原价:" + data.getPrice());
                String[] strings = data.getImages().split("\\|");
                final ArrayList<String> imageUrls = new ArrayList<>();
                for (int i = 0; i < strings.length; i++) {
                    imageUrls.add(strings[i]);
                }
                banner.setImages(imageUrls);
                //bannner点击事件进行跳转
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(DetailActivity.this, ImageScaleActivity.class);
                        //传递的数据...整个轮播图数据的集合需要传递,,,当前展示的图片的位置需要传递postion
                        //intent传递可以传的数据...基本数据类型...引用数据类型(必须序列化,所有的类,包括内部类实现serilizable接口)...bundle
                        intent.putStringArrayListExtra("imageUrls", imageUrls);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
                banner.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCartAddSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            AddCartBean addCartBean = new Gson().fromJson(string, AddCartBean.class);
            Toast.makeText(DetailActivity.this, addCartBean.getMsg(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.detail_image_back, R.id.detail_share, R.id.watch_cart, R.id.detai_add_cart})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.detail_image_back://返回
                finish();
                break;
            case R.id.detail_share://分享......分享的是商品链接
                if (deatilBean != null) {
                    DeatilBean.DataBean data = deatilBean.getData();
                    ShareUtil.shareWeb(DetailActivity.this, data.getDetailUrl(), data.getTitle(), "我在京东发现一个好的商品,赶紧来看看吧!", data.getImages().split("\\|")[0], R.mipmap.ic_launcher);
                }
                break;
            case R.id.watch_cart://查看购物车....跳转的是购物车的activity
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.detai_add_cart://添加购物车...uid...pid...判断是否登录
                if (CommonUtil.getBoolean("isLogin")) {
                    addCartPresenter.addToCart(Constant.ADD_CART_URL, CommonUtil.getString("uid"), pid);
                } else {
                    Intent intent2 = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(DetailActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        EventBus.getDefault().unregister(this);
    }
}
