package com.wd.tech.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.CommunityUserCommentAdapter;
import com.wd.tech.bean.CommunityUserCommentListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.CommunityUserCommentListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class CommunityUserCommentActivity extends WDActivity implements CustomAdapt {

    private CommunityUserCommentListPresenter communityUserCommentListPresenter;
    private int userId;
    private String sessionId;
    private LoginUserInfoBean userInfo;
    private CommunityUserCommentAdapter communityUserCommentAdapter;
    @BindView(R.id.usercommentlist_recycler)
    RecyclerView recyclerView;
    private int size;
    @BindView(R.id.usercommentlist_tv1)
    TextView textView;
    private int id;
    private String headPic;
    private String nickName;
    @BindView(R.id.usercommentlist_sim)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.usercommentlist_tv)
    TextView textView1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_user_comment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        userInfo = getUserInfo(this);
        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        headPic = intent.getStringExtra("headPic");
        nickName = intent.getStringExtra("nickName");

        simpleDraweeView.setImageURI(Uri.parse(headPic));
        textView1.setText(nickName);

        communityUserCommentListPresenter = new CommunityUserCommentListPresenter(new CommunityUserCommentList());
        communityUserCommentListPresenter.request(userId, sessionId, id, 1, 100);

        communityUserCommentAdapter = new CommunityUserCommentAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(communityUserCommentAdapter);
    }

    @OnClick(R.id.usercommentlist_back)
    public void usercommentlist_back() {
        finish();
    }


    @Override
    protected void destoryData() {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class CommunityUserCommentList implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            List<CommunityUserCommentListBean> result = (List<CommunityUserCommentListBean>) data.getResult();
            size = result.size();
            textView.setText(size + "条评论");
            communityUserCommentAdapter.addItem(result);
            communityUserCommentAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
