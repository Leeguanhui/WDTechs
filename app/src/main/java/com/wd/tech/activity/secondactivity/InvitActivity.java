package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.InvitRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.FindMyPostListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DeletePostPresnter;
import com.wd.tech.presenter.FindMyPostPresnter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class InvitActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefresh;
    private FindMyPostPresnter findMyPostPresnter;
    private LoginUserInfoBean userInfo;
    int mPage = 1;
    int mCount = 1000;
    private InvitRecycleAdapter invitRecycleAdapter;
    private DeletePostPresnter deletePostPresnter;
    public int position = 0;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        dialog = CircularLoading.showLoadDialog(this, "", true);
        deletePostPresnter = new DeletePostPresnter(new DeleteResult());
        findMyPostPresnter = new FindMyPostPresnter(new FindMyPostResult());
        invitRecycleAdapter = new InvitRecycleAdapter(this);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setAdapter(invitRecycleAdapter);
        userInfo = getUserInfo(this);
        findMyPostPresnter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        invitRecycleAdapter.setItemClickListener(new InvitRecycleAdapter.itemClickListener() {
            @Override
            public void getUserId(int positions,int id) {
                position = positions;
                deletePostPresnter.request(userInfo.getUserId(), userInfo.getSessionId(), String.valueOf(id));
            }
        });
    }
    @OnClick(R.id.back_imag)
    public void back_imag(){
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

    /**
     * 帖子列表
     */
    private class FindMyPostResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<FindMyPostListBean> findMyPostListBeans = (List<FindMyPostListBean>) result.getResult();
                invitRecycleAdapter.addAll(findMyPostListBeans);
                CircularLoading.closeDialog(dialog);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 删除帖子
     */
    private class DeleteResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            String status = result.getStatus();
            if (status.equals("0000")) {
                invitRecycleAdapter.removeByPosition(position);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
