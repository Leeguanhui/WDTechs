package com.wd.tech.activity;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.FindGroupNoticePageListAdapter;
import com.wd.tech.bean.FindGroupNoticePageList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupNoticePageListPresenter;
import com.wd.tech.presenter.ReviewGroupApplyPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupChatActivity extends WDActivity implements XRecyclerView.LoadingListener {


    @BindView(R.id.group_chat_xrecyclerview)
    XRecyclerView groupChatXrecyclerview;

    private FindGroupNoticePageListAdapter findGroupNoticePageListAdapter;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean infoBean;
    private FindGroupNoticePageListPresenter findGroupNoticePageListPresenter;
    private ReviewGroupApplyPresenter reviewGroupApplyPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_chat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        findGroupNoticePageListPresenter = new FindGroupNoticePageListPresenter(new GroupChatData());
        reviewGroupApplyPresenter = new ReviewGroupApplyPresenter(new Review());
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        groupChatXrecyclerview.setLayoutManager(linearLayoutManager);
        findGroupNoticePageListAdapter = new FindGroupNoticePageListAdapter(this);
        groupChatXrecyclerview.setAdapter(findGroupNoticePageListAdapter);
        groupChatXrecyclerview.setLoadingListener(this);
        findGroupNoticePageListPresenter.request(userId, sessionId, 1, 10);
        findGroupNoticePageListAdapter.setOnItemClickListener(new FindGroupNoticePageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int noticeId) {
                reviewGroupApplyPresenter.request(userId,sessionId,noticeId,1);
            }
        });
        findGroupNoticePageListAdapter.setOnItemClickListener1(new FindGroupNoticePageListAdapter.OnItemClickListener1() {
            @Override
            public void onItemClick1(int noticeId) {
                reviewGroupApplyPresenter.request(userId,sessionId,noticeId,2);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
        findGroupNoticePageListPresenter.request(userId, sessionId, 1, 10);
    }

    @OnClick(R.id.group_chat_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        if (findGroupNoticePageListPresenter.isRunning()) {
            groupChatXrecyclerview.refreshComplete();
            return;
        }
        findGroupNoticePageListPresenter.request(userId, sessionId, 1, 10);

    }

    @Override
    public void onLoadMore() {
        if (findGroupNoticePageListPresenter.isRunning()) {
            groupChatXrecyclerview.loadMoreComplete();
            return;
        }
        findGroupNoticePageListPresenter.request(userId, sessionId, 1, 10);

    }

    class GroupChatData implements ICoreInfe<Result<List<FindGroupNoticePageList>>> {

        @Override
        public void success(Result<List<FindGroupNoticePageList>> result) {
            groupChatXrecyclerview.refreshComplete();
            groupChatXrecyclerview.loadMoreComplete();
            findGroupNoticePageListAdapter.clear();
            if (result.getStatus().equals("0000")) {
                findGroupNoticePageListAdapter.addAll(result.getResult());
                findGroupNoticePageListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }

    protected void onDestroy() {
        super.onDestroy();
        findGroupNoticePageListPresenter.unBind();
    }

    private class Review implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                findGroupNoticePageListPresenter.request(userId, sessionId, 1, 10);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
