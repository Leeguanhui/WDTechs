package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.AttenRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class AttenActivity extends WDActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.xrecycle)
    XRecyclerView mXRecycle;
    private AttenRecycleAdapter attenRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_atten;
    }

    @Override
    protected void initView() {
        attenRecycleAdapter = new AttenRecycleAdapter();
        mXRecycle.setLayoutManager(new LinearLayoutManager(this));
        mXRecycle.setAdapter(attenRecycleAdapter);
        mXRecycle.setPullRefreshEnabled(true);
        mXRecycle.setLoadingMoreEnabled(false);
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 上下拉加载刷新
     */
    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {

    }
}
