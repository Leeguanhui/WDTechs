package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.IntegRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class IntegActivity extends WDActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.integ_xrecycle)
    XRecyclerView integ_xrecycle;
    private IntegRecycleAdapter integRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integ;
    }

    @Override
    protected void initView() {
        integRecycleAdapter = new IntegRecycleAdapter();
        integ_xrecycle.setLayoutManager(new LinearLayoutManager(this));
        integ_xrecycle.setAdapter(integRecycleAdapter);
        integ_xrecycle.setPullRefreshEnabled(true);
        integ_xrecycle.setLoadingMoreEnabled(false);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
