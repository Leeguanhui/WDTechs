package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wd.tech.R;
import com.wd.tech.activity.secondactivity.SettingActivity;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindConversationListPresenter;
import com.wd.tech.presenter.InitFriendListPresenter;
import com.wd.tech.presenter.QueryFriendInformationPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IMActivity extends WDActivity {


    @BindView(R.id.im_iv_query_finsh)
    ImageView imIvQueryFinsh;
    @BindView(R.id.im_iv_query_name)
    TextView imIvQueryName;
    @BindView(R.id.im_iv_query_ziliao)
    ImageView imIvQueryZiliao;
    private FriendInfoList friendInfoList;
    private int userId;
    private String sessionId;
    private InitFriendListPresenter presenter;
    private FindConversationList findConversationList;
    private FindConversationListPresenter findConversationListPresenter;
    private LoginUserInfoBean bean;
    private String headPic;
    private String nickName;
    private String userName;
    private String userNames;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findConversationListPresenter = new FindConversationListPresenter(new OB());
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        bean = getUserInfo(this);
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
            nickName = bean.getNickName();
            userName = bean.getUserName();
            headPic = bean.getHeadPic();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.hx_ok, chatFragment).commit();
        Intent intent = getIntent();
        friendInfoList = (FriendInfoList) intent.getSerializableExtra("friendInfoList");
        userNames = intent.getStringExtra("userNames");

        findConversationListPresenter.request(userId, sessionId, userNames);
    }

    @Override
    protected void destoryData() {

    }

    private void setEaseUser() {
        EaseUI easeUI = EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username) {
        EaseUser easeUser = new EaseUser(username);
        if (username.equals(userName.toLowerCase())) {
            easeUser.setNickname(nickName);
            easeUser.setAvatar(headPic);
        } else {
            easeUser.setNickname(findConversationList.getNickName());
            easeUser.setAvatar(findConversationList.getHeadPic());
        }
        return easeUser;
    }

    //即可正常显示头像昵称
    @OnClick({R.id.im_iv_query_finsh, R.id.im_iv_query_ziliao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_iv_query_finsh:
                finish();
                break;
            case R.id.im_iv_query_ziliao:
                Intent intent = new Intent(this, ChatSettingsActivity.class);
                intent.putExtra("findConversationList", findConversationList);
                startActivity(intent);
                break;
        }
    }


    private class Qura implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class OB implements ICoreInfe<Result<List<FindConversationList>>> {
        @Override
        public void success(Result<List<FindConversationList>> data) {
            if (data.getStatus().equals("0000")) {
                findConversationList = data.getResult().get(0);
                imIvQueryName.setText(findConversationList.getNickName());
                setEaseUser();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
