package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
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
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.FindUserTaskListPresenter;
import com.wd.tech.presenter.WheWeChatPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class TaskActivity extends WDActivity implements CustomAdapt {

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
    @BindView(R.id.sign_texts)
    TextView mSigntext;
    @BindView(R.id.comment_text)
    TextView mCommenttext;
    @BindView(R.id.tiezi_text)
    TextView mTiezitext;
    @BindView(R.id.fenxiang_text)
    TextView mFenxiangtext;
    @BindView(R.id.guanggao_text)
    TextView mGuanggaotext;
    @BindView(R.id.xinxi_text)
    TextView mXinxitext;
    @BindView(R.id.weixin_text)
    TextView mWeixintext;
    private IWXAPI mWechatApi;
    static int type = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = getUserInfo(this);
        findUserTaskListPresenter = new FindUserTaskListPresenter(new FindUserTaskResult());
        mTasklinear.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        findUserTaskListPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
    }

    @OnClick(R.id.sign_bnt)
    public void mSignBtn() {
        startActivity(new Intent(TaskActivity.this, SignCalendarActivity.class));
        finish();
    }

    @OnClick(R.id.comment_bnt)
    public void mCommentBnt() {
        Intent intent = new Intent(TaskActivity.this, MainActivity.class);
        intent.putExtra("comment", 1);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tiezi_bnt)
    public void mTieziBnt() {
        startActivity(new Intent(TaskActivity.this, ReleasePostActivity.class));
        finish();
    }

    @OnClick(R.id.xinxi_bnt)
    public void mXinxi() {
        startActivity(new Intent(TaskActivity.this, UpdateMessageActivity.class));
        finish();
    }

    @OnClick(R.id.weixin_bnt)
    public void mWeixin() {
        type=2;
        mWechatApi = WXAPIFactory.createWXAPI(TaskActivity.this, "wx4c96b6b8da494224", false);
        mWechatApi.registerApp("wx4c96b6b8da494224");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        mWechatApi.sendReq(req);
    }

    public static int getType() {
        return type;
    }
    public static void setType() {
        type = 1;
    }
    @OnClick(R.id.guanggao_bnt)
    public void wGuang() {
        startActivity(new Intent(TaskActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.fenxiang_bnt)
    public void wFenXinag() {
        startActivity(new Intent(TaskActivity.this, MainActivity.class));
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
                            mSigntext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mSignbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mSignbnt.setTextColor(Color.WHITE);
                            mSignbnt.setClickable(false);
                        } else {
                            mSigntext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mSignbnt.setText("去签到");
                            mSignbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 1:
                        if (status == 1) {
                            mCommenttext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mCommentbnt.setText("已完成");
                            mCommentbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mCommentbnt.setTextColor(Color.WHITE);
                            mCommentbnt.setClickable(false);
                        } else {
                            mCommenttext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mCommentbnt.setText("去评论");
                            mCommentbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 2:
                        if (status == 1) {
                            mTiezitext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mTiezibnt.setText("已完成");
                            mTiezibnt.setBackgroundResource(R.drawable.btn_shapert);
                            mTiezibnt.setTextColor(Color.WHITE);
                            mTiezibnt.setClickable(false);
                        } else {
                            mTiezitext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mTiezibnt.setText("去发帖");
                            mTiezibnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 3:
                        if (status == 1) {
                            mFenxiangtext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mFenxiangbnt.setText("已完成");
                            mFenxiangbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mFenxiangbnt.setTextColor(Color.WHITE);
                            mFenxiangbnt.setClickable(false);
                        } else {
                            mFenxiangtext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mFenxiangbnt.setText("去分享");
                            mFenxiangbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 4:
                        if (status == 1) {
                            mGuanggaotext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mGuanggaobnt.setText("已完成");
                            mGuanggaobnt.setBackgroundResource(R.drawable.btn_shapert);
                            mGuanggaobnt.setTextColor(Color.WHITE);
                            mGuanggaobnt.setClickable(false);
                        } else {
                            mGuanggaotext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mGuanggaobnt.setText("去查看");
                            mGuanggaobnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 5:
                        if (status == 1) {
                            mXinxitext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mXinxibnt.setText("已完成");
                            mXinxibnt.setBackgroundResource(R.drawable.btn_shapert);
                            mXinxibnt.setTextColor(Color.WHITE);
                            mXinxibnt.setClickable(false);
                        } else {
                            mXinxitext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mXinxibnt.setText("去完善");
                            mXinxibnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                    case 6:
                        if (status == 1) {
                            mWeixintext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mWeixinbnt.setText("已完成");
                            mWeixinbnt.setBackgroundResource(R.drawable.btn_shapert);
                            mWeixinbnt.setTextColor(Color.WHITE);
                            mWeixinbnt.setClickable(false);
                        } else {
                            mWeixintext.setText("+" + findUserTaskListBean.getTaskIntegral());
                            mWeixinbnt.setText("去绑定");
                            mWeixinbnt.setBackgroundResource(R.drawable.btn_shaperf);
                        }
                        break;
                }
            }
            mTasklinear.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
