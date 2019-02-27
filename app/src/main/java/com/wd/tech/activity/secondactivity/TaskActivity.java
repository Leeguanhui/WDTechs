package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wd.tech.R;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.ReleasePostActivity;
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
    LinearLayout mTasklinear;
    private FindUserTaskListPresenter findUserTaskListPresenter;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.sign_bnt)
    Button mSignbnt;
    @BindView(R.id.comment_bnt)
    Button mCommentbnt;
    @BindView(R.id.tiezi_bnt)
    Button mTiezibnt;
    @BindView(R.id.fenxiang_bnt)
    Button mFenxiangbnt;
    @BindView(R.id.guanggao_bnt)
    Button mGuanggaobnt;
    @BindView(R.id.xinxi_bnt)
    Button mXinxibnt;
    @BindView(R.id.weixin_bnt)
    Button mWeixinbnt;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        findUserTaskListPresenter = new FindUserTaskListPresenter(new FindUserTaskResult());
        mTasklinear.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        findUserTaskListPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        dialog = CircularLoading.showLoadDialog(TaskActivity.this, "加载中...", true);
    }

    @OnClick(R.id.sign_bnt)
    public void mSignBtn() {
        startActivity(new Intent(TaskActivity.this, SignCalendarActivity.class));
    }

    @OnClick(R.id.comment_bnt)
    public void mCommentBnt() {
        Intent intent = new Intent(TaskActivity.this, MainActivity.class);
        intent.putExtra("comment", 1);
        startActivity(intent);
    }

    @OnClick(R.id.tiezi_bnt)
    public void mTieziBnt() {
        startActivity(new Intent(TaskActivity.this, ReleasePostActivity.class));
    }

    @OnClick(R.id.xinxi_bnt)
    public void mXinxi() {
        startActivity(new Intent(TaskActivity.this, UpdateMessageActivity.class));
    }

    @OnClick(R.id.weixin_bnt)
    public void mWeixin(){
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
                            mSignbnt.setText("已完成");
                            mSignbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mSignbnt.setTextColor(Color.WHITE);
                            mSignbnt.setClickable(false);
                        } else {
                            mSignbnt.setText("去签到");
                            mSignbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 1:
                        if (status == 1) {
                            mCommentbnt.setText("已完成");
                            mCommentbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mCommentbnt.setTextColor(Color.WHITE);
                            mCommentbnt.setClickable(false);
                        } else {
                            mCommentbnt.setText("去评论");
                            mCommentbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 2:
                        if (status == 1) {
                            mTiezibnt.setText("已完成");
                            mTiezibnt.setBackgroundResource(R.drawable.btn_shapert);
                            mTiezibnt.setTextColor(Color.WHITE);
                            mTiezibnt.setClickable(false);
                        } else {
                            mTiezibnt.setText("去发帖");
                            mTiezibnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 3:
                        if (status == 1) {
                            mFenxiangbnt.setText("已完成");
                            mFenxiangbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mFenxiangbnt.setTextColor(Color.WHITE);
                            mFenxiangbnt.setClickable(false);
                        } else {
                            mFenxiangbnt.setText("去分享");
                            mFenxiangbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 4:
                        if (status == 1) {
                            mGuanggaobnt.setText("已完成");
                            mGuanggaobnt.setBackgroundResource(R.drawable.btn_shapert);
                            mGuanggaobnt.setTextColor(Color.WHITE);
                            mGuanggaobnt.setClickable(false);
                        } else {
                            mGuanggaobnt.setText("去查看");
                            mGuanggaobnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 5:
                        if (status == 1) {
                            mXinxibnt.setText("已完成");
                            mXinxibnt.setBackgroundResource(R.drawable.btn_shapert);
                            mXinxibnt.setTextColor(Color.WHITE);
                            mXinxibnt.setClickable(false);
                        } else {
                            mXinxibnt.setText("去完善");
                            mXinxibnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 6:
                        if (status == 1) {
                            mWeixinbnt.setText("已完成");
                            mWeixinbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mWeixinbnt.setTextColor(Color.WHITE);
                            mWeixinbnt.setClickable(false);
                        } else {
                            mWeixinbnt.setText("去绑定");
                            mWeixinbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                }
            }
            CircularLoading.closeDialog(dialog);
            mTasklinear.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
