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
    private int userId;
    private String sessionId;
    private FindConversationList findConversationList;
    private LoginUserInfoBean bean;
    private String userNames;
    private FindConversationListPresenter findConversationListPresenter;
    private String nickName;
    private String headPic;
    private long userId1;
    private String userName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findConversationListPresenter = new FindConversationListPresenter(new OB());
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.hx_ok, chatFragment).commit();
        Intent intent = getIntent();
        bean = getUserInfo(this);
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
        userNames = intent.getStringExtra("userNames");

        findConversationListPresenter.request(userId, sessionId, userNames);

//        imIvQueryName.setText(friendInfoList.getRemarkName());
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.im_iv_query_finsh, R.id.im_iv_query_ziliao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_iv_query_finsh:
                finish();
                break;
            case R.id.im_iv_query_ziliao:
                Intent intent = new Intent(this, ChatSettingsActivity.class);
                intent.putExtra("userId1", userId1);
                intent.putExtra("nickName", nickName);
                intent.putExtra("headPic", headPic);
                intent.putExtra("userName", userName);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;
        }
    }

    private class OB implements ICoreInfe<Result<List<FindConversationList>>> {


        @Override
        public void success(Result<List<FindConversationList>> data) {
            if (data.getStatus().equals("0000")) {
                findConversationList = data.getResult().get(0);
                nickName = findConversationList.getNickName();
                headPic = findConversationList.getHeadPic();
                userId1 = findConversationList.getUserId();
                userName = findConversationList.getUserName();
                imIvQueryName.setText(findConversationList.getNickName());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
