package com.bwie.myapplication.view.acitvity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.MessageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView web_view;
    private String detailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

//        detailUrl = getIntent().getStringExtra("detailUrl");
        if (detailUrl != null) {
            web_view.loadUrl(detailUrl);
            web_view.setWebViewClient(new WebViewClient());
            WebSettings settings = web_view.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getMessage(MessageBean messageBean){
        detailUrl = messageBean.getUrl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
