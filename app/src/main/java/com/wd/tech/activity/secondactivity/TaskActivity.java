package com.wd.tech.activity.secondactivity;

import android.content.Intent;
import android.widget.Button;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindUserTaskListPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskActivity extends WDActivity {


    private FindUserTaskListPresenter findUserTaskListPresenter;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.sign_bnt)
    Button sign_bnt;
    @BindView(R.id.comment_bnt)
    Button comment_bnt;
    @BindView(R.id.tiezi_bnt)
    Button tiezi_bnt;
    @BindView(R.id.fenxiang_bnt)
    Button fenxiang_bnt;
    @BindView(R.id.guanggao_bnt)
    Button guanggao_bnt;
    @BindView(R.id.xinxi_bnt)
    Button xinxi_bnt;
    @BindView(R.id.weixin_bnt)
    Button weixin_bnt;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        findUserTaskListPresenter = new FindUserTaskListPresenter(new FindUserTaskResult());
        findUserTaskListPresenter.request(userInfo.getSessionId(), userInfo.getSessionId());
    }

    @OnClick(R.id.sign_bnt)
    public void mSignBtn() {
        startActivity(new Intent(TaskActivity.this, SignCalendarActivity.class));
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 查看任务列表
     */
    private class FindUserTaskResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
