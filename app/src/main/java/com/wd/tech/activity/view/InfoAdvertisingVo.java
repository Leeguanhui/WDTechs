package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoAdvertisingVo extends WDActivity {
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_advertising_vo;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
       /* WebSettings settings = webview.getSettings();
        settings.setTextZoom(250); // 通过百分比来设置文字的大小，默认值是100。
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);*/
        webview.loadUrl(id);

    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
