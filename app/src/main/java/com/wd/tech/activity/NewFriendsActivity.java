package com.wd.tech.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.FindFriendNoticePageListAdapter;
import com.wd.tech.activity.adapter.FindGroupNoticePageListAdapter;
import com.wd.tech.bean.FindFriendNoticePageList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindFriendNoticePageListPresenter;
import com.wd.tech.presenter.ReviewFriendApplyPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewFriendsActivity extends WDActivity implements XRecyclerView.LoadingListener{

    private String sessionId;
    private int userId;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerviewFriend;

    private FindFriendNoticePageListAdapter pageListAdapter;
    FindFriendNoticePageListPresenter listPresenter = new FindFriendNoticePageListPresenter(new FriendData());
    private ReviewFriendApplyPresenter reviewFriendApplyPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friends;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        reviewFriendApplyPresenter = new ReviewFriendApplyPresenter(new Review());
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xrecyclerviewFriend.setLayoutManager(linearLayoutManager);
        pageListAdapter = new FindFriendNoticePageListAdapter(this);
        xrecyclerviewFriend.setAdapter(pageListAdapter);
        xrecyclerviewFriend.setLoadingListener(this);
        listPresenter.request(userId,sessionId,1,10);
        pageListAdapter.setOnItemClickListener(new FindFriendNoticePageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int noticeId) {
                reviewFriendApplyPresenter.request(userId,sessionId,noticeId,2);
            }
        });
        pageListAdapter.setOnItemClickListener1(new FindFriendNoticePageListAdapter.OnItemClickListener1() {
            @Override
            public void onItemClick1(int noticeId) {
                reviewFriendApplyPresenter.request(userId,sessionId,noticeId,3);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.new_firend_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void onRefresh() {
        if (listPresenter.isRunning()){
            xrecyclerviewFriend.refreshComplete();
            return;
        }
        listPresenter.request(userId,sessionId,1,10);

    }

    @Override
    public void onLoadMore() {
        if (listPresenter.isRunning()){
            xrecyclerviewFriend.loadMoreComplete();
            return;
        }
        listPresenter.request(userId,sessionId,1,10);

    }

    class FriendData implements ICoreInfe<Result<List<FindFriendNoticePageList>>> {
        @Override
        public void success(Result<List<FindFriendNoticePageList>> result) {
            xrecyclerviewFriend.refreshComplete();
            xrecyclerviewFriend.loadMoreComplete();
            pageListAdapter.clear();
            if (result.getStatus().equals("0000")){
                pageListAdapter.addAll(result.getResult());
                pageListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }


    }

    protected void onDestroy() {
        super.onDestroy();
        listPresenter.unBind();
    }

    private class Review implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(NewFriendsActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                listPresenter.request(userId, sessionId, 1, 10);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
