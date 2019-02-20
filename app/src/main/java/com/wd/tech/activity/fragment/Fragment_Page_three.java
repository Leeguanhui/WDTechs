package com.wd.tech.activity.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.ReleasePostActivity;
import com.wd.tech.activity.adapter.CommunityListAdapter;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.CommunityListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_three extends WDFragment implements XRecyclerView.LoadingListener {

    private CommunityListPresenter communityListPresenter;
    @BindView(R.id.communitylist_recycler)
    XRecyclerView recyclerView;
    private CommunityListAdapter communityListAdapter;
    private int page = 1;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_three;
    }

    @Override
    protected void initView() {
        communityListPresenter = new CommunityListPresenter(new CommunityList());
        communityListPresenter.request(1, 5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        communityListAdapter = new CommunityListAdapter(getContext());
        recyclerView.setAdapter(communityListAdapter);

        //上下拉
        recyclerView.setLoadingListener(this);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
    }

    /**
     * 上下拉
     */
    @Override
    public void onRefresh() {
        page = 1;
        communityListAdapter.removeAll();

        communityListAdapter.notifyDataSetChanged();
        communityListPresenter.request(page, 5);
    }

    @Override
    public void onLoadMore() {
        page++;
        communityListPresenter.request(page, 5);
    }

    private class CommunityList implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            List<CommunityListBean> circleBeans = (List<CommunityListBean>) data.getResult();
            communityListAdapter.addItem(circleBeans);
            communityListAdapter.notifyDataSetChanged();
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 发布帖子
     */
    @OnClick(R.id.add_community)
    public void add_community() {
        startActivity(new Intent(getContext(), ReleasePostActivity.class));
    }
}
