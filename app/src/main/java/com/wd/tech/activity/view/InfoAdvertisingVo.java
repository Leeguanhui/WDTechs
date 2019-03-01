package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DoTheTaskPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoAdvertisingVo extends WDActivity {
    @BindView(R.id.webview)
    WebView webview;
    private DoTheTaskPresenter doTheTaskPresenter;
    private LoginUserInfoBean userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_advertising_vo;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheResult());
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
        doTheTaskPresenter.request(userInfo.getUserId(),userInfo.getSessionId(),1005);
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

    /**
     * 做任务
     */
    private class DoTheResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
