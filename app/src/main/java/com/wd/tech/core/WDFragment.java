package com.wd.tech.core;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.LoginUserInfoBeanDao;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;


/**
 * 作者：夏洪武
 * 时间：2019/1/22.
 * 邮箱：
 * 说明：
 */
public abstract class WDFragment extends Fragment implements CustomAdapt {
    public Gson mGson = new Gson();
    public Dialog mLoadDialog;
    private Unbinder unbinder;

    private ConnectivityManager connectivityManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        long time = System.currentTimeMillis();
        View view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }
    /**
     * 数据库
     */
    public LoginUserInfoBean getUserInfo(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, LoginUserInfoBeanDao.TABLENAME);
        LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
        List<LoginUserInfoBean> list = loginUserInfoBeanDao.queryBuilder().where(LoginUserInfoBeanDao.Properties.Statu.eq("1"))
                .build().list();
        if (list.size() > 0) {
            LoginUserInfoBean loginUserInfoBean = list.get(0);
            return loginUserInfoBean;
        }
        return null;
    }

    /**
     * 清空数据库
     */
    public void deleteUserInfo(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, LoginUserInfoBeanDao.TABLENAME);
        LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
        loginUserInfoBeanDao.deleteAll();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initLoad() {
        mLoadDialog = new ProgressDialog(getContext());// 加载框
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
    //取消操作：请求或者其他
    public void cancelLoadDialog() {

    }
    /**
     * 设置页面名字 用于友盟统计
     */
    public abstract String getPageName();
    /**
     * 设置layoutId
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


}
