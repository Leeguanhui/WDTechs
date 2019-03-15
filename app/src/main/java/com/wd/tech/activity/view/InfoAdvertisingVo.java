package com.wd.tech.activity.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.Raffle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoAdvertisingVo extends WDActivity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.prog)
    ProgressBar prog;
    private DoTheTaskPresenter doTheTaskPresenter;
    private LoginUserInfoBean userInfo;
    private Raffle raffle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_advertising_vo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //raffle = new Raffle(new RaffleCallBack());
        userInfo = getUserInfo(this);
        /*if(userInfo!=null){
            raffle.request(userInfo.getUserId(),userInfo.getSessionId());
        }*/
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheResult());
        WebSettings mWebSettings = webview.getSettings();
        /* 设置支持Js,必须设置的,不然网页基本上不能看 */
        mWebSettings.setJavaScriptEnabled(true);
        /* 设置WebView是否可以由JavaScript自动打开窗口，默认为false，通常与JavaScript的window.open()配合使用。*/
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /* 设置缓存模式,我这里使用的默认,不做多讲解 */
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        mWebSettings.setDomStorageEnabled(true);
        /* 设置为使用webview推荐的窗口 */
        mWebSettings.setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        mWebSettings.setLoadWithOverviewMode(true);
        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWebSettings.setGeolocationEnabled(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWebSettings.setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        /* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        webview.setHorizontalScrollBarEnabled(false);
        /* 指定垂直滚动条是否有叠加样式 */
        webview.setVerticalScrollbarOverlay(true);
        /* 设置滚动条的样式 */
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.addJavascriptInterface(new JsInteration(), "btnStart");
        /* 这个不用说了,重写WebChromeClient监听网页加载的进度,从而实现进度条 */
        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                prog.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        /* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        webview.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                prog.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                prog.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(InfoAdvertisingVo.this, "加载失败", Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });
        webview.loadUrl(id);
        if(userInfo!=null){
            doTheTaskPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), 1005);
        }
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
    /**
     * js调用android的方法
     */
    class JsInteration {
        @JavascriptInterface
        public void toastMessage(String message) {
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "评价成功", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(DetailsActivity.this, MainActivity.class));
        }

        @JavascriptInterface
        public void onSumResult(int result) {
            Toast.makeText(getApplicationContext(), "我是android调用js方法(4.4前)，入参是1和2，js返回结果是" + result, Toast.LENGTH_LONG).show();
        }
    }

    private class RaffleCallBack implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
             Toast.makeText(InfoAdvertisingVo.this,data.getPrizeName(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
