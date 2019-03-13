package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.CheckMyFriendPresnter;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendlyActivity extends WDActivity {


    @BindView(R.id.image_head)
    SimpleDraweeView imageHead;
    @BindView(R.id.user_niname)
    TextView userNiname;
    @BindView(R.id.user_integral)
    TextView userIntegral;
    @BindView(R.id.user_qianmin)
    TextView userQianmin;
    @BindView(R.id.user_sex)
    TextView userSex;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.btn_r_add)
    Button btnRAdd;
    @BindView(R.id.user_riqi)
    TextView userRiqi;
    @BindView(R.id.user_youxiang)
    TextView userYouxiang;
    @BindView(R.id.vip)
    ImageView Vip;
    @BindView(R.id.btn_r_message)
    Button btnRMessage;
    private int userid1;
    private String headPic;
    private String nickName;
    private String signature;
    private String sessionId;
    private int userId;
    private String phone;
    private CheckMyFriendPresnter checkMyFriendPresnter;
    private String ss;
    private Dialog dialog;
    private int flag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friendly;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
            phone = infoBean.getPhone();
        }
        dialog = CircularLoading.showLoadDialog(this, "加载中...", true);

        checkMyFriendPresnter = new CheckMyFriendPresnter(new Check());
        Intent intent = getIntent();
        userid1 = intent.getIntExtra("userid1", 0);
        String phone1 = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        ss = intent.getStringExtra("sss");
        nickName = intent.getStringExtra("nickName");
        int sex = intent.getIntExtra("sex", 1);
        headPic = intent.getStringExtra("headPic");
        int integral = intent.getIntExtra("integral", 0);
        signature = intent.getStringExtra("signature");
        long birthday = intent.getLongExtra("birthday", 0);
        int whetherVip = intent.getIntExtra("whetherVip", 0);
        if (sex == 1) {
            userSex.setText("男");
        } else if (sex == 2) {
            userSex.setText("女");
        }
        if (signature != null) {
            userQianmin.setText(signature);
        } else {
            userQianmin.setText("暂无介绍");
        }
        imageHead.setImageURI(headPic);
        userNiname.setText(nickName);

        String s = String.valueOf(birthday);
        if (birthday == 0) {
            userRiqi.setText("(暂无出生年月)");
        } else {
            userRiqi.setText("(" + birthday + ")");
        }
        if (email != null) {
            userYouxiang.setText("" + email + "");
        } else {
            userYouxiang.setText("暂无邮箱");
        }


        if (whetherVip == 1) {
            Vip.setVisibility(View.VISIBLE);
        } else if (whetherVip == 2) {
            Vip.setVisibility(View.GONE);
        }
        userIntegral.setText("(" + integral + "积分)");

        userPhone.setText(phone1);
        if (ss.equals(phone)) {
            btnRAdd.setVisibility(View.GONE);
            btnRMessage.setVisibility(View.GONE);
            CircularLoading.closeDialog(dialog);
        } else if (ss.equals(phone1)) {
            checkMyFriendPresnter.request(userId, sessionId, userid1);
        }

    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.btn_r_add, R.id.btn_r_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_r_add:
                Intent intent = new Intent(this, FriendRequestActivity.class);
                intent.putExtra("headPic", headPic);
                intent.putExtra("nickName", nickName);
                intent.putExtra("signature", signature);
                intent.putExtra("userid1", userid1);
                startActivity(intent);
                break;
            case R.id.btn_r_message:
//                if (flag == 1) {
//                    Intent intent1 = new Intent(getApplicationContext(), IMActivity.class);
//                    intent1.putExtra("userNames", nickName);
//                    startActivity(intent1);
//                }
                break;
        }

    }

    private class Check implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                flag = data.getFlag();
                if (flag == 1) {
                    btnRAdd.setVisibility(View.GONE);
                    btnRMessage.setVisibility(View.VISIBLE);
                } else if (flag == 2) {
                    btnRAdd.setVisibility(View.VISIBLE);
                    btnRMessage.setVisibility(View.GONE);
                }
                CircularLoading.closeDialog(dialog);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
