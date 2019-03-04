package com.wd.tech.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.wd.tech.presenter.CreateGroupPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlockActivity extends WDActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.qun_edittext_name)
    EditText qunEdittextName;
    @BindView(R.id.qun_edittext_content)
    EditText qunEdittextContent;
    @BindView(R.id.text_login)
    TextView textLogin;
    private String namE;
    private String contenT;
    private CreateGroupPresenter groupPresenter;
    private LoginUserInfoBean infoBean;
    private String sessionId;
    private int userId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_flock;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        groupPresenter = new CreateGroupPresenter(new Creat());
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R.id.back, R.id.text_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.text_login:
                namE = qunEdittextName.getText().toString();
                contenT = qunEdittextContent.getText().toString();
                if (TextUtils.isEmpty(contenT)){
                    Toast.makeText(this, "群名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contenT)){
                    Toast.makeText(this, "介绍不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                groupPresenter.request(userId,sessionId,namE,contenT);
                break;
        }
    }

    private class Creat implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
             if (data.getStatus().equals("0000")){
                 Toast.makeText(FlockActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                 finish();
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
