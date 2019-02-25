package com.wd.tech.activity.secondactivity;

import android.content.Intent;
import android.widget.EditText;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ModifyNickNamePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateMessageActivity extends WDActivity {


    private ModifyNickNamePresenter modifyNickNamePresenter;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.my_name)
    EditText my_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_message;
    }

    @Override
    protected void initView() {
        modifyNickNamePresenter = new ModifyNickNamePresenter(new ModifyNameResult());
        userInfo = getUserInfo(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        my_name.setText(name);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        String name = my_name.getText().toString();
        modifyNickNamePresenter.request(userInfo.getUserId(), userInfo.getSessionId(), name);
    }

    /**
     * 修改用户名
     */
    private class ModifyNameResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
