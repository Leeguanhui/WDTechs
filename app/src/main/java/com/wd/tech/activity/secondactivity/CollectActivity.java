package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.CollectRecycleAdapter;
import com.wd.tech.bean.FindCollectBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindAllCollectionPresenter;

import java.util.List;

import butterknife.BindView;

public class CollectActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.collect_xrecycle)
    XRecyclerView collect_xrecycle;
    private CollectRecycleAdapter collectRecycleAdapter;
    private FindAllCollectionPresenter findAllCollectionPresenter;
    private int mPage = 1;
    private int mCount = 10000;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


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
        collect_xrecycle.setLoadingListener(this);
        findAllCollectionPresenter = new FindAllCollectionPresenter(new FindColleResult());
        userInfo = getUserInfo(this);
        findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(CollectActivity.this, "刷新", Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(CollectActivity.this, "加载", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        collectRecycleAdapter.deleteAll();
        findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 收藏列表
     */
    private class FindColleResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<FindCollectBean> findColleResults = (List<FindCollectBean>) result.getResult();
            collectRecycleAdapter.addAll(findColleResults);
            collectRecycleAdapter.notifyDataSetChanged();
            collect_xrecycle.refreshComplete();
            collect_xrecycle.loadMoreComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
