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
    ImageView back;
    @BindView(R.id.fragment1_search_edit)
    EditText fragment1SearchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.aaaaa)
    RelativeLayout aaaaa;
    @BindView(R.id.receylview)
    RecyclerView receylview;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    private String sessionId;
    private int userId;
    private FindGroupsByUserIdPresenter presenter;
    private FindGroupsByUserIdAdapter adapter;
    private List<FindGroupsByUserId> result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cluster;
    }

    @Override
    protected void initView() {
        LoginUserInfoBean bean = getUserInfo(this);
        presenter = new FindGroupsByUserIdPresenter(new GroupFind());
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
        presenter.request(userId,sessionId);
        adapter = new FindGroupsByUserIdAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        receylview.setLayoutManager(layoutManager);
        receylview.setAdapter(adapter);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.request(userId,sessionId);
                adapter.addItem(result);
                adapter.notifyDataSetChanged();
                smart.finishRefresh();
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
                result = data.getResult();
                Toast.makeText(ClusterActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.addItem(result);
                adapter.notifyDataSetChanged();
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
