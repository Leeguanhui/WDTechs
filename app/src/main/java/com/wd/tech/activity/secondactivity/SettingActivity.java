package com.wd.tech.activity.secondactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.math.BigDecimal;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.thirdlyactivity.SignatureActivity;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ByIdUserInfoPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends WDActivity {

    @BindView(R.id.my_header)
    SimpleDraweeView my_header;
    @BindView(R.id.my_name)
    TextView my_name;
    @BindView(R.id.my_sex)
    TextView my_sex;
    @BindView(R.id.my_brith)
    TextView my_brith;
    @BindView(R.id.my_phone)
    TextView my_phone;
    @BindView(R.id.my_mail)
    TextView my_mail;
    @BindView(R.id.my_jifen)
    TextView my_jifen;
    @BindView(R.id.my_vip)
    TextView my_vip;
    @BindView(R.id.my_face)
    TextView my_face;
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        LoginUserInfoBean userInfo = getUserInfo(this);
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
    }

    @OnClick(R.id.dropout)
    public void dropout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
        alert.setTitle("提示");
        alert.setMessage("是否退出登录");
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserInfo(SettingActivity.this);
                edit.putString("mysign", "");
                edit.commit();
                finish();
            }
        });
        alert.setNegativeButton("取消", null);
        alert.show();
    }

    @OnClick(R.id.back_bnt)
    public void back_bnt() {
        finish();
    }

    @OnClick(R.id.line1)
    public void line1() {
        startActivity(new Intent(SettingActivity.this, SignatureActivity.class));
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 根据ID查询信息
     */
    private class ByIdUserResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            ByIdUserInfoBean byIdUserInfoBean = (ByIdUserInfoBean) result.getResult();
            my_header.setImageURI(Uri.parse(byIdUserInfoBean.getHeadPic()));
            my_name.setText(byIdUserInfoBean.getNickName());
            my_sex.setText(byIdUserInfoBean.getSex());
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date.setTime(byIdUserInfoBean.getBirthday());
            String Datetime = format.format(date);
            my_brith.setText(Datetime);
            my_phone.setText(byIdUserInfoBean.getPhone());
            my_mail.setText(byIdUserInfoBean.getEmail());
            my_jifen.setText(byIdUserInfoBean.getIntegral() + "");
            if (byIdUserInfoBean.getWhetherVip() == 1) {
                my_vip.setText("是");
            } else {
                my_vip.setText("否");
            }
            if (byIdUserInfoBean.getWhetherFaceId() == 1) {
                my_vip.setText("已绑定");
            } else {
                my_vip.setText("未绑定");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
