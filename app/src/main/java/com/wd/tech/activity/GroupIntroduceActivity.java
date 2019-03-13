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
import com.wd.tech.presenter.ModifyGroupPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupIntroduceActivity extends WDActivity {
    @BindView(R.id.groupintro_back)
    ImageView groupintroBack;
    @BindView(R.id.groupintro_finish)
    TextView groupintroFinish;
    @BindView(R.id.groupintro_edit)
    EditText groupintroEdit;
    private ModifyGroupPresenter modifyGroupPresenter;
    private String s;
    private int groupintro;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean bean;
    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        groupintro = intent.getIntExtra("groupintro", 0);
        modifyGroupPresenter = new ModifyGroupPresenter(new Modify());

        bean = getUserInfo(this);
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_introduce;
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R.id.groupintro_back, R.id.groupintro_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.groupintro_back:
                finish();
                break;
            case R.id.groupintro_finish:
                s = groupintroEdit.getText().toString();
                modifyGroupPresenter.request(userId,sessionId,groupintro,s);
                break;
        }
    }
    class Modify implements ICoreInfe<Result> {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000")){
                Toast.makeText(GroupIntroduceActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
