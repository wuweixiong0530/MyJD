package com.bwie.myapplication.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.FenLeiBean;
import com.bwie.myapplication.model.bean.HomeBean;
import com.bwie.myapplication.model.bean.MessageBean;
import com.bwie.myapplication.presenter.FragmentHomeP;
import com.bwie.myapplication.util.ChenJinUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.GlideImageLoader;
import com.bwie.myapplication.view.acitvity.CustomCaptrueActivity;
import com.bwie.myapplication.view.acitvity.DetailActivity;
import com.bwie.myapplication.view.acitvity.SuoSouActivity;
import com.bwie.myapplication.view.acitvity.WebViewActivity;
import com.bwie.myapplication.view.adapter.HengXiangAdapter;
import com.bwie.myapplication.view.adapter.MiaoShaAdapter;
import com.bwie.myapplication.view.adapter.TuiJianAdapter;
import com.bwie.myapplication.view.custom.ObservableScrollView;
import com.bwie.myapplication.view.iview.InterFragmentHome;
import com.bwie.myapplication.view.iview.OnItemListner;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class FragmentHome extends Fragment implements InterFragmentHome {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.heng_xiang_recycler)
    RecyclerView heng_xiang_recycler;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.miao_sha_recycler)
    RecyclerView miao_sha_recycler;
    @BindView(R.id.tui_jian_recycler)
    RecyclerView tui_jian_recycler;
    @BindView(R.id.observe_scroll_view)
    ObservableScrollView observe_scroll_view;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.sao_yi_sao)
    LinearLayout sao_yi_sao;
    @BindView(R.id.linear_include)
    LinearLayout linear_include;
    @BindView(R.id.sousuo)
    LinearLayout sousuo;
    private Unbinder unbinder;

    private FragmentHomeP fragmentHomeP;
    private final String TAG_MARGIN_ADDED = "marginAdded";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ChenJinUtil.setStatusBarColor(getActivity(), Color.TRANSPARENT);
        initTitleBar();
        fragmentHomeP = new FragmentHomeP(this);
        fragmentHomeP.getNetData(Constant.HOME_URL);
        fragmentHomeP.getFenLeiData(Constant.FEN_LEI_URL);
        initBanner();
        initMarqueeView();
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                smart_refresh.finishLoadMore(2000);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                smart_refresh.finishRefresh(2000);
            }
        });
        sao_yi_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomCaptrueActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuoSouActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMarqueeView() {
        List<String> info = new ArrayList<>();
        info.add("欢迎访问京东app");
        info.add("大家有没有在");
        info.add("是不是还有人在睡觉");
        info.add("你在旁边看着呢");
        info.add("赶紧的好好学习吧 马上毕业了");
        info.add("你没有事件了");
        marqueeView.startWithList(info);
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.isAutoPlay(true);
        banner.setDelayTime(2500);
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessHome(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
            List<HomeBean.DataBean> datas = homeBean.getData();
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                imageUrls.add(datas.get(i).getIcon());
            }
            banner.setImages(imageUrls);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    HomeBean.DataBean dataBean = homeBean.getData().get(position);
                    if (dataBean.getType() == 0) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("detailUrl", dataBean.getUrl());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "即将跳转商品详情", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            banner.start();
            miao_sha_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
            MiaoShaAdapter miaoShaAdapter = new MiaoShaAdapter(getActivity(), homeBean.getMiaosha());
            miao_sha_recycler.setAdapter(miaoShaAdapter);
            miaoShaAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    String detailUrl = homeBean.getMiaosha().getList().get(position).getDetailUrl();
                    MessageBean messageBean = new MessageBean(null, detailUrl,null);
                    EventBus.getDefault().postSticky(messageBean);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position) {

                }
            });
            tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
            TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(getActivity(), homeBean.getTuijian());
            tui_jian_recycler.setAdapter(tuiJianAdapter);
            tuiJianAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    int pid = homeBean.getTuijian().getList().get(position).getPid();
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    MessageBean messageBean = new MessageBean(null, null, String.valueOf(pid));
                    EventBus.getDefault().postSticky(messageBean);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position) {
                    int pid = homeBean.getTuijian().getList().get(position).getPid();
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    MessageBean messageBean = new MessageBean(null, null, String.valueOf(pid));
                    EventBus.getDefault().postSticky(messageBean);
                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessFenLei(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            FenLeiBean fenLeiBean = new Gson().fromJson(string, FenLeiBean.class);
            heng_xiang_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.HORIZONTAL, false));
            HengXiangAdapter hengXiangAdapter = new HengXiangAdapter(getActivity(), fenLeiBean);
            heng_xiang_recycler.setAdapter(hengXiangAdapter);
            hengXiangAdapter.setOnItemListner(new OnItemListner() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getActivity(), "点击事件执行", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position) {
                    Toast.makeText(getActivity(), "长按事件执行", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("http://")) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("detaiUrl", result);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "暂不支持此二维码", Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private void initTitleBar() {

        //linearLayout在view绘制的时候外面包裹一层relativeLayout
        //应该尽量减少使用linearLayout...view优化

        addMargin();


        ViewTreeObserver viewTreeObserver = linear_include.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linear_include.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                final int height = linear_include.getHeight();

                observe_scroll_view.setScrollViewListener(new ObservableScrollView.IScrollViewListener() {
                    @Override
                    public void onScrollChanged(int x, int y, int oldx, int oldy) {
                        if (y <= 0) {
                            addMargin();
                            ChenJinUtil.setStatusBarColor(getActivity(),Color.TRANSPARENT);

                            linear_include.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或
                        } else if (y > 0 && y < height) {

                            if (y > ChenJinUtil.getStatusBarHeight(getActivity())) {
                                //去掉margin
                                removeMargin();
                                //状态栏为灰色
                                ChenJinUtil.setStatusBarColor(getActivity(),getResources().getColor(R.color.colorPrimaryDark));
                            }

                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            // 只是layout背景透明(仿知乎滑动效果)
                            linear_include.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
                        } else {
                            linear_include.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
                        }
                    }
                });
            }
        });

    }

    private void removeMargin() {
        if (TAG_MARGIN_ADDED.equals(linear_include.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linear_include.getLayoutParams();
            // 移除的间隔大小就是状态栏的高度
            params.topMargin -= ChenJinUtil.getStatusBarHeight(getActivity());
            linear_include.setLayoutParams(params);
            linear_include.setTag(null);
        }
    }

    private void addMargin() {
        //给标题调价margin
        if (!TAG_MARGIN_ADDED.equals(linear_include.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linear_include.getLayoutParams();
            // 添加的间隔大小就是状态栏的高度
            params.topMargin += ChenJinUtil.getStatusBarHeight(getActivity());
            linear_include.setLayoutParams(params);
            linear_include.setTag(TAG_MARGIN_ADDED);
        }
    }
}
