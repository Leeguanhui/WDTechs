package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.AttenRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.AttUserListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AttUserListPresenter;

import java.util.List;

import butterknife.BindView;

public class AttenActivity extends WDActivity {
    @BindView(R.id.xrecycle)
    XRecyclerView mXRecycle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private AttenRecycleAdapter attenRecycleAdapter;
    private AttUserListPresenter attUserListPresenter;
    int mPage = 1;
    int mCount = 1000;
    private LoginUserInfoBean userInfo;
    private Dialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_atten;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        attUserListPresenter = new AttUserListPresenter(new AttUserResult());
        attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        attenRecycleAdapter = new AttenRecycleAdapter();
        mXRecycle.setLayoutManager(new LinearLayoutManager(this));
        mXRecycle.setAdapter(attenRecycleAdapter);
        mXRecycle.setPullRefreshEnabled(true);
        mXRecycle.setLoadingMoreEnabled(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                attenRecycleAdapter.deleteAll();
                attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
        dialog = CircularLoading.showLoadDialog(AttenActivity.this, "加载中...", true);

    }

    @Override
    protected void destoryData() {

    }


    /**
     * 关注用户列表
     */
    private class AttUserResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            List<AttUserListBean> resultResult = (List<AttUserListBean>) result.getResult();
            attenRecycleAdapter.addAll(resultResult);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
