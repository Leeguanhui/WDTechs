package com.wd.tech.activity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 作者：夏洪武
 * 时间：2019/2/20.
 * 邮箱：
 * 说明：
 */
public class NoScrollWebView extends WebView {
    public NoScrollWebView(Context context) {
        super(context);
    }

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                int maxOverScrollY, boolean isTouchEvent) {
        return true;
    }

    /**
     * 使WebView不可滚动
     */
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }



}
