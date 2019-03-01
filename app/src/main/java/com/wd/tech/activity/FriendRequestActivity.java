package com.wd.tech.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddFriendPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendRequestActivity extends WDActivity {

    @BindView(R.id.back)
    ImageView backI;
    @BindView(R.id.send)
    TextView senD;
    @BindView(R.id.user_heada)
    SimpleDraweeView userHeada;
    @BindView(R.id.user_namea)
    TextView userNamea;
    @BindView(R.id.user_xiangqing)
    TextView userXiangqing;
    @BindView(R.id.apply_for)
    EditText applyFor;
    private String sessionId;
    private int userId;
    private AddFriendPresenter addFriendPresenter;
    private int userId1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected void initView() {
        addFriendPresenter = new AddFriendPresenter(new AddFre());
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
        Intent intent = getIntent();
        String headPic = intent.getStringExtra("headPic");
        String nickName = intent.getStringExtra("nickName");
        String signature = intent.getStringExtra("signature");
        userId1 = intent.getIntExtra("userid1", 0);
        userHeada.setImageURI(headPic);
        userNamea.setText(nickName);
        userXiangqing.setText(signature);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R.id.send, R.id.back})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.send:
                String apply = applyFor.getText().toString();
                if (TextUtils.isEmpty(apply)) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                addFriendPresenter.request(userId, sessionId, userId1, apply);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
    }

    private class AddFre implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("1001")) {
                Toast.makeText(FriendRequestActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
