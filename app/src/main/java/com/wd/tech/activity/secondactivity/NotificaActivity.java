package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.NofitiRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NotifiListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.SysNoticeListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class NotificaActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.notifica_xrecycle)
    RecyclerView mNotificaxrecycle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private NofitiRecycleAdapter mNofitiRecycleAdapter;
    private SysNoticeListPresenter sysNoticeListPresenter;
    private LoginUserInfoBean userInfo;
    int mPage = 1;
    int mCount = 1000;
    private Dialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifica;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = getUserInfo(this);
        sysNoticeListPresenter = new SysNoticeListPresenter(new SysNotResult());
        sysNoticeListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        dialog = CircularLoading.showLoadDialog(NotificaActivity.this, "加载中...", true);
        mNofitiRecycleAdapter = new NofitiRecycleAdapter();
        mNotificaxrecycle.setLayoutManager(new LinearLayoutManager(this));
        mNotificaxrecycle.setAdapter(mNofitiRecycleAdapter);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.back_imag)
    public void back_imag(){
        finish();
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    /**
     * 消息通知
     */
    private class SysNotResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            mNofitiRecycleAdapter.addAll((List<NotifiListBean>) result.getResult());
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadmore();
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
