package com.wd.tech.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.RsaCoder;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.LoginUserInfoBeanDao;
import com.wd.tech.presenter.LoginUserInfoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.mTv_Reg)
    TextView mTv_Reg;
    @BindView(R.id.mEt_Phone_Login)
    EditText mEt_Phone_Login;
    @BindView(R.id.mEd_Pwd_Login)
    EditText mEd_Pwd_Login;
    private LoginUserInfoPresenter loginUserInfoPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginUserInfoPresenter = new LoginUserInfoPresenter(new LoginResult());
        //跳转到注册页面
        mTv_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.mBt_Login)
    public void mBt_Login() {
        String phone = mEt_Phone_Login.getText().toString();
        String pwd = mEd_Pwd_Login.getText().toString();
        try {
            String RSAPwd = RsaCoder.encryptByPublicKey(pwd);
            loginUserInfoPresenter.request(phone, RSAPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 登录回调接口
     */
    private class LoginResult implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                DaoSession daoSession = DaoMaster.newDevSession(LoginActivity.this, LoginUserInfoBeanDao.TABLENAME);
                LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
                loginUserInfoBeanDao.deleteAll();
                LoginUserInfoBean loginUserInfoBean = (LoginUserInfoBean) data.getResult();
                loginUserInfoBean.setStatu(1);
                loginUserInfoBeanDao.insertOrReplace(loginUserInfoBean);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}