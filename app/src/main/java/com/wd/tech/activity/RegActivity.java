package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.RsaCoder;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.RegUserInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegActivity extends WDActivity {

    @BindView(R.id.mEt_Name_Reg)
    EditText mEt_Name_Reg;
    @BindView(R.id.mEt_Phone_Reg)
    EditText mEt_Phone_Reg;
    @BindView(R.id.mEt_Pwd_Reg)
    EditText mEt_Pwd_Reg;
    private RegUserInfoPresenter regUserInfoPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reg;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        regUserInfoPresenter = new RegUserInfoPresenter(new RegResult());
    }

    @OnClick(R.id.mBt_Reg)
    public void mBt_Reg() {
        String name = mEt_Name_Reg.getText().toString();
        String phone = mEt_Phone_Reg.getText().toString();
        String pwd = mEt_Pwd_Reg.getText().toString();
        boolean phoneLegal = StringUtils.isPhoneLegal(phone);
        if (name.equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (pwd.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!phoneLegal) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwd.length() < 8) {
                Toast.makeText(this, "密码不能少于八位", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        try {
            String RSAPwd = RsaCoder.encryptByPublicKey(pwd);
            regUserInfoPresenter.request(phone, name, RSAPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void destoryData() {
    }

    /**
     * 注册回调接口
     */
    private class RegResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(RegActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegActivity.this,LoginActivity.class));
            if (result.getStatus().equals("0000")) {
                EMClient.getInstance().login(mEt_Phone_Reg.getText().toString(),mEt_Pwd_Reg.getText().toString(),new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
