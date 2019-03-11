package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.QunChengYuanAdapter;
import com.wd.tech.bean.GroupMember;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupMemberListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupMembersActivity extends WDActivity {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.delet_im)
    ImageView deletIm;
    @BindView(R.id.x_recycler)
    RecyclerView xRecycler;
    private FindGroupMemberListPresenter findGroupMemberListPresenter;
    private int userid;
    private String session1d;
    private int groupId;
    private QunChengYuanAdapter qunChengYuanAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_members;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findGroupMemberListPresenter = new FindGroupMemberListPresenter(new FindGroup());
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            session1d = infoBean.getSessionId();
            userid = infoBean.getUserId();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecycler.setLayoutManager(linearLayoutManager);
        qunChengYuanAdapter = new QunChengYuanAdapter(this);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 0);
        findGroupMemberListPresenter.request(userid, session1d, groupId);
        xRecycler.setAdapter(qunChengYuanAdapter);
        qunChengYuanAdapter.setOnItemClickListener(new QunChengYuanAdapter.ClickListener() {
            @Override
            public void click(int id) {

            }
        });

    }

    @Override
    protected void destoryData() {

    }



    @OnClick(R.id.delet_im)
    public void onViewClicked() {
    }

    private class FindGroup implements ICoreInfe<Result<List<GroupMember>>> {

        @Override
        public void success(Result<List<GroupMember>> data) {
            if (data.getStatus().equals("0000")){
                List<GroupMember> result = data.getResult();
                qunChengYuanAdapter.addItem(result);
                qunChengYuanAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
