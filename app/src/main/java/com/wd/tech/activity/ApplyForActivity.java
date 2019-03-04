package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ApplyAddGroupPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyForActivity extends WDActivity {

    @BindView(R.id.want_add_group_back)
    ImageView wantAddGroupBack;
    @BindView(R.id.want_add_group_send)
    TextView wantAddGroupSend;
    @BindView(R.id.want_add_group_message)
    EditText wantAddGroupMessage;
    @BindView(R.id.want_add_group_num)
    TextView wantAddGroupNum;
    private String sessionId;
    private int userId;
    private ApplyAddGroupPresenter applyAddGroupPresenter;
    private int groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_for;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 0);
        applyAddGroupPresenter = new ApplyAddGroupPresenter(new Apply());
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.want_add_group_back, R.id.want_add_group_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.want_add_group_back:
                finish();
                break;
            case R.id.want_add_group_send:
                String s = wantAddGroupMessage.getText().toString();
                applyAddGroupPresenter.request(userId,sessionId,groupId,s);
                break;
        }
    }

    private class Apply implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
             if (data.getStatus().equals("0000")){
                 Toast.makeText(ApplyForActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                 finish();
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
