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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupChatActivity extends WDActivity implements XRecyclerView.LoadingListener{


    @BindView(R.id.group_chat_xrecyclerview)
    XRecyclerView groupChatXrecyclerview;

    private FindGroupNoticePageListAdapter adapter;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean bean;
    private FindGroupNoticePageListPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_chat;
    }

    @Override
    protected void initView() {
        presenter = new FindGroupNoticePageListPresenter(new GroupChatData());
        bean = getUserInfo(this);
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        groupChatXrecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new FindGroupNoticePageListAdapter(this);
        groupChatXrecyclerview.setAdapter(adapter);
        groupChatXrecyclerview.setLoadingListener(this);
        presenter.request(userId,sessionId,true,5);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.group_chat_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        if (presenter.isRunning()){
            groupChatXrecyclerview.refreshComplete();
            return;
        }
        presenter.request(userId,sessionId,true,5);

    }

    @Override
    public void onLoadMore() {
        if (presenter.isRunning()){
            groupChatXrecyclerview.loadMoreComplete();
            return;
        }
        presenter.request(userId,sessionId,false,5);

    }

    class GroupChatData implements ICoreInfe<Result<List<FindGroupNoticePageList>>> {

        @Override
        public void success(Result<List<FindGroupNoticePageList>> result) {
            groupChatXrecyclerview.refreshComplete();
            groupChatXrecyclerview.loadMoreComplete();
            adapter.clear();
            if (result.getStatus().equals("0000")){
                adapter.addAll(result.getResult());
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }

    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }
}
