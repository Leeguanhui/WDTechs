package com.wd.tech.activity.thirdlyactivity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.wd.tech.R;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.Constant;
import com.wd.tech.core.utils.EncryptUtil;
import com.wd.tech.core.utils.RsaCoder;
import com.wd.tech.presenter.UpdatePwdPresnter;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePwdActivity extends WDActivity {

    @BindView(R.id.currentpwd_text)
    EditText mPwdEdt;
    @BindView(R.id.resetpwd_editext)
    EditText mNewPwd;
    private UpdatePwdPresnter updatePwdPresnter;
    private LoginUserInfoBean userInfo;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = getUserInfo(this);
        updatePwdPresnter = new UpdatePwdPresnter(new UpdatePwdResult());
    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        String odlPwd = mPwdEdt.getText().toString();
        String newPwd = mNewPwd.getText().toString();
        String odlerPwd = null;
        String newpwd = null;
        try {
            odlerPwd = RsaCoder.encryptByPublicKey(odlPwd);
            newpwd = RsaCoder.encryptByPublicKey(newPwd);
            updatePwdPresnter.request(userInfo.getUserId(), userInfo.getSessionId(), odlerPwd, newpwd);
            dialog = CircularLoading.showLoadDialog(UpdatePwdActivity.this, "", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 修改密码
     */
    private class UpdatePwdResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                CircularLoading.closeDialog(dialog);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
