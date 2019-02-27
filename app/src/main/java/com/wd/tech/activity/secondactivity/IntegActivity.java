package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.IntegRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class IntegActivity extends WDActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.integ_xrecycle)
    XRecyclerView mIntegxrecycle;
    private IntegRecycleAdapter mIntegRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integ;
    }

    @Override
    protected void initView() {
        mIntegRecycleAdapter = new IntegRecycleAdapter();
        mIntegxrecycle.setLayoutManager(new LinearLayoutManager(this));
        mIntegxrecycle.setAdapter(mIntegRecycleAdapter);
        mIntegxrecycle.setPullRefreshEnabled(true);
        mIntegxrecycle.setLoadingMoreEnabled(false);
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
