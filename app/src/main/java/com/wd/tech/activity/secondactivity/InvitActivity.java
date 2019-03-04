package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
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
    private View view;
    private PopupWindow pop;
    private TextView cancle,delete;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        view = View.inflate(this, R.layout.delete_view, null);
        dialog = CircularLoading.showLoadDialog(this, "", true);
        deletePostPresnter = new DeletePostPresnter(new DeleteResult());
        findMyPostPresnter = new FindMyPostPresnter(new FindMyPostResult());
        pop = new PopupWindow(view, (LinearLayout.LayoutParams.WRAP_CONTENT), (LinearLayout.LayoutParams.WRAP_CONTENT), false);
        pop.setContentView(view);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        invitRecycleAdapter = new InvitRecycleAdapter(this);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setAdapter(invitRecycleAdapter);
        userInfo = getUserInfo(this);
        findMyPostPresnter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        invitRecycleAdapter.setItemClickListener(new InvitRecycleAdapter.itemClickListener() {
            @Override
            public void getUserId(int positions, int ids) {
                position = positions;
                id = ids;
                pop.showAtLocation(InvitActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
        cancle = view.findViewById(R.id.cancle_btn);
        delete = view.findViewById(R.id.delete_bnt);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePostPresnter.request(userInfo.getUserId(), userInfo.getSessionId(), String.valueOf(id));
            }
        });
    }

    @OnClick(R.id.back_imag)
    public void back_imag() {
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
                pop.dismiss();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
