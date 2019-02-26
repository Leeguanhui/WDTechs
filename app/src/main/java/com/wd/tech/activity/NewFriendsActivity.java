package com.wd.tech.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.FindFriendNoticePageListAdapter;
import com.wd.tech.bean.FindFriendNoticePageList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindFriendNoticePageListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewFriendsActivity extends WDActivity implements XRecyclerView.LoadingListener{

    private String sessionId;
    private int userId;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerviewFriend;

    private FindFriendNoticePageListAdapter adapter;
    FindFriendNoticePageListPresenter presenter = new FindFriendNoticePageListPresenter(new FriendData());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friends;
    }

    @Override
    protected void initView() {
        LoginUserInfoBean bean = getUserInfo(this);
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xrecyclerviewFriend.setLayoutManager(linearLayoutManager);
        adapter = new FindFriendNoticePageListAdapter(this);
        xrecyclerviewFriend.setAdapter(adapter);
        xrecyclerviewFriend.setLoadingListener(this);
        presenter.request(userId,sessionId,true,5);
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
        if (presenter.isRunning()){
            xrecyclerviewFriend.refreshComplete();
            return;
        }
        presenter.request(userId,sessionId,true,5);

    }

    @Override
    public void onLoadMore() {
        if (presenter.isRunning()){
            xrecyclerviewFriend.loadMoreComplete();
            return;
        }
        presenter.request(userId,sessionId,false,5);

    }

    class FriendData implements ICoreInfe<Result<List<FindFriendNoticePageList>>> {
        @Override
        public void success(Result<List<FindFriendNoticePageList>> result) {
            xrecyclerviewFriend.refreshComplete();
            xrecyclerviewFriend.loadMoreComplete();
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
