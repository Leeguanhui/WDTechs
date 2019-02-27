package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.IntegRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.UserIntegralBean;
import com.wd.tech.bean.UserIntegralListBean;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.IntegralRecordPresenter;
import com.wd.tech.presenter.UserIntegralPresenter;

import java.util.List;

import butterknife.BindView;

public class IntegActivity extends WDActivity  {
    @BindView(R.id.integ_xrecycle)
    XRecyclerView mIntegxrecycle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private IntegRecycleAdapter mIntegRecycleAdapter;
    private UserIntegralPresenter userIntegralPresenter;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.integ_text)
    TextView mIntegtext;
    private Dialog dialog;
    private IntegralRecordPresenter integralRecordPresenter;
    int mPage = 1;
    int mCount = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integ;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        integralRecordPresenter = new IntegralRecordPresenter(new IntegralRecordResult());
        integralRecordPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        userIntegralPresenter = new UserIntegralPresenter(new UserIntegralResult());
        userIntegralPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        dialog = CircularLoading.showLoadDialog(IntegActivity.this, "加载中...", true);
        mIntegRecycleAdapter = new IntegRecycleAdapter();
        mIntegxrecycle.setLayoutManager(new LinearLayoutManager(this));
        mIntegxrecycle.setAdapter(mIntegRecycleAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mIntegRecycleAdapter.deleteAll();
                integralRecordPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                integralRecordPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 查询用户积分
     */
    private class UserIntegralResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            UserIntegralBean resultResult = (UserIntegralBean) result.getResult();
            mIntegtext.setText(resultResult.getAmount() + "");
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 明细
     */
    private class IntegralRecordResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            List<UserIntegralListBean> integralListBeans = (List<UserIntegralListBean>) result.getResult();
            mIntegRecycleAdapter.addAll(integralListBeans);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
