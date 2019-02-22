package com.wd.tech.activity.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.ReleasePostActivity;
import com.wd.tech.activity.adapter.CommunityListAdapter;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddCommunityGreatPresenter;
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
    private String sessionId;
    private int userId;
    private LoginUserInfoBean userInfo;
    private AddCommunityGreatPresenter addCommunityGreatPresenter;

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

        if (userInfo == null) {
            userId = 0;
            sessionId = "0";
        } else {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }

        communityListPresenter = new CommunityListPresenter(new CommunityList());
        communityListPresenter.request(userId, sessionId, page, 5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        communityListAdapter = new CommunityListAdapter(getContext());
        recyclerView.setAdapter(communityListAdapter);

        //点赞
        addCommunityGreatPresenter = new AddCommunityGreatPresenter(new AddCommunityGreat());

        //上下拉
        recyclerView.setLoadingListener(this);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfo = getUserInfo(getContext());
        communityListAdapter.removeAll();
        communityListPresenter.request(userId, sessionId, page, 5);
    }

    /**
     * 上下拉
     */
    @Override
    public void onRefresh() {
        page = 1;
        communityListAdapter.removeAll();

        communityListAdapter.notifyDataSetChanged();
        communityListPresenter.request(userId, sessionId, page, 5);
    }

    @Override
    public void onLoadMore() {
        page++;
        communityListPresenter.request(userId, sessionId, page, 5);
    }

    private class CommunityList implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
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
        if (userInfo != null) {
            startActivity(new Intent(getContext(), ReleasePostActivity.class));
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private class AddCommunityGreat implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
