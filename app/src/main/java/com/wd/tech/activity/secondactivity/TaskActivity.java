package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wd.tech.R;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.FindUserTaskListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindUserTaskListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskActivity extends WDActivity {

    @BindView(R.id.task_linear)
    LinearLayout task_linear;
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
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        findUserTaskListPresenter = new FindUserTaskListPresenter(new FindUserTaskResult());
        findUserTaskListPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        task_linear.setVisibility(View.GONE);
        dialog = CircularLoading.showLoadDialog(TaskActivity.this, "加载中...", true);
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
            List<FindUserTaskListBean> findUserTaskResults = (List<FindUserTaskListBean>) result.getResult();
            for (int i = 0; i < findUserTaskResults.size(); i++) {
                FindUserTaskListBean findUserTaskListBean = findUserTaskResults.get(i);
                int status = findUserTaskListBean.getStatus();
                switch (i) {
                    case 0:
                        if (status == 1) {
                            sign_bnt.setText("已完成");
                            sign_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            sign_bnt.setTextColor(Color.WHITE);
                            sign_bnt.setClickable(false);
                        } else {
                            sign_bnt.setText("去签到");
                            sign_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 1:
                        if (status == 1) {
                            comment_bnt.setText("已完成");
                            comment_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            comment_bnt.setTextColor(Color.WHITE);
                            comment_bnt.setClickable(false);
                        } else {
                            comment_bnt.setText("去评论");
                            comment_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 2:
                        if (status == 1) {
                            tiezi_bnt.setText("已完成");
                            tiezi_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            tiezi_bnt.setTextColor(Color.WHITE);
                            tiezi_bnt.setClickable(false);
                        } else {
                            tiezi_bnt.setText("去发帖");
                            tiezi_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 3:
                        if (status == 1) {
                            fenxiang_bnt.setText("已完成");
                            fenxiang_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            fenxiang_bnt.setTextColor(Color.WHITE);
                            fenxiang_bnt.setClickable(false);
                        } else {
                            fenxiang_bnt.setText("去分享");
                            fenxiang_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 4:
                        if (status == 1) {
                            guanggao_bnt.setText("已完成");
                            guanggao_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            guanggao_bnt.setTextColor(Color.WHITE);
                            guanggao_bnt.setClickable(false);
                        } else {
                            guanggao_bnt.setText("去查看");
                            guanggao_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 5:
                        if (status == 1) {
                            xinxi_bnt.setText("已完成");
                            xinxi_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            xinxi_bnt.setTextColor(Color.WHITE);
                            xinxi_bnt.setClickable(false);
                        } else {
                            xinxi_bnt.setText("去完善");
                            xinxi_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 6:
                        if (status == 1) {
                            weixin_bnt.setText("已完成");
                            weixin_bnt.setBackgroundResource(R.drawable.btn_shapert);
                            weixin_bnt.setTextColor(Color.WHITE);
                            weixin_bnt.setClickable(false);
                        } else {
                            weixin_bnt.setText("去绑定");
                            weixin_bnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                }
            }
            CircularLoading.closeDialog(dialog);
            task_linear.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
