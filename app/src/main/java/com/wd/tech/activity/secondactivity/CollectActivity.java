package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.CollectRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class CollectActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.collect_xrecycle)
    XRecyclerView collect_xrecycle;
    private CollectRecycleAdapter collectRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        collectRecycleAdapter = new CollectRecycleAdapter();
        collect_xrecycle.setLayoutManager(new LinearLayoutManager(this));
        collect_xrecycle.setAdapter(collectRecycleAdapter);
        collect_xrecycle.setLoadingMoreEnabled(false);
        collect_xrecycle.setPullRefreshEnabled(true);
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
