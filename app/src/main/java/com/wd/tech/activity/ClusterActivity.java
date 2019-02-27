package com.wd.tech.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.FindGroupsByUserIdAdapter;
import com.wd.tech.bean.FindGroupsByUserId;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupInfoPresenter;
import com.wd.tech.presenter.FindGroupsByUserIdPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClusterActivity extends WDActivity {

    @BindView(R.id.back)
    ImageView backI;
    @BindView(R.id.fragment1_search_edit)
    EditText fragment1SearchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.aaaaa)
    RelativeLayout mAaaa;
    @BindView(R.id.receylview)
    RecyclerView receylView;
    @BindView(R.id.smart)
    SmartRefreshLayout smarT;
    private String sessionId;
    private int userId;
    private FindGroupsByUserIdPresenter findGroupsByUserIdPresenter;
    private FindGroupsByUserIdAdapter findGroupsByUserIdAdapter;
    private List<FindGroupsByUserId> userIdList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cluster;
    }

    @Override
    protected void initView() {
        LoginUserInfoBean infoBean = getUserInfo(this);
        findGroupsByUserIdPresenter = new FindGroupsByUserIdPresenter(new GroupFind());
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
        findGroupsByUserIdPresenter.request(userId,sessionId);
        findGroupsByUserIdAdapter = new FindGroupsByUserIdAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        receylView.setLayoutManager(layoutManager);
        receylView.setAdapter(findGroupsByUserIdAdapter);
        smarT.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                findGroupsByUserIdPresenter.request(userId,sessionId);
                findGroupsByUserIdAdapter.addItem(userIdList);
                findGroupsByUserIdAdapter.notifyDataSetChanged();
                smarT.finishRefresh();
            }
        });
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.back, R.id.search_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_image:
                String s = fragment1SearchEdit.getText().toString();
                break;
        }
    }


    private class GroupFind implements ICoreInfe<Result<List<FindGroupsByUserId>>> {
        @Override
        public void success(Result<List<FindGroupsByUserId>> data) {
            if (data.getStatus().equals("0000")){
                userIdList = data.getResult();
                Toast.makeText(ClusterActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                findGroupsByUserIdAdapter.addItem(userIdList);
                findGroupsByUserIdAdapter.notifyDataSetChanged();
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
