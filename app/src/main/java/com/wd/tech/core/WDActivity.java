package com.wd.tech.core;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;


import butterknife.ButterKnife;

public abstract class WDActivity extends AppCompatActivity {
    private static String addr;
    public Dialog mLoadDialog;// 加载框
    /**
     * 记录处于前台的Activity
     */
    private static WDActivity mForegroundActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoad();
        setContentView(getLayoutId());
        ButterKnife.bind(this);//绑定布局
        initView();

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    /**
     * @param mActivity 传送Activity的
     * @param
     */
    public void intent(Class mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * 初始化加载框
     */
    private void initLoad() {
        mLoadDialog = new ProgressDialog(this);// 加载框
        mLoadDialog.setCanceledOnTouchOutside(false);
        mLoadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (mLoadDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
                    cancelLoadDialog();//加载消失的同时
                    mLoadDialog.cancel();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryData();
    }

    //取消操作：请求或者其他
    public void cancelLoadDialog() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static WDActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static String getdz() {
        return addr;
    }
}
