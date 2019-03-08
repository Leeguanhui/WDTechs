package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wd.tech.R;
import com.wd.tech.bean.FindGroupsByUserId;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupsByUserIdPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IGActivity extends WDActivity {


    @BindView(R.id.im_iv_query_finsh)
    ImageView imIvQueryFinsh;
    @BindView(R.id.im_iv_query_name)
    TextView imIvQueryName;
    @BindView(R.id.im_iv_query_ziliao)
    ImageView imIvQueryZiliao;
    private FindGroupsByUserId findGroupsByUserId;
    private String userNames;
    private FindGroupsByUserIdPresenter presenter;
    private LoginUserInfoBean infoBean;
    private String sessionId;
    private int userId;
    private String groupImage;
    private String groupName;
    private int groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new FindGroupsByUserIdPresenter(new Qun());
        EaseChatFragment chatFragment = new EaseChatFragment();
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.hx_ok, chatFragment).commit();
        Intent intent = getIntent();
        userNames = intent.getStringExtra("userNames");
        presenter.request(userId, sessionId);
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
                Intent intent = new Intent(this, GroupDetailsSettingsActivity.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupName", groupName);
                intent.putExtra("groupImage", groupImage);
                startActivity(intent);
                break;
        }
    }


    private class Qun implements ICoreInfe<Result<List<FindGroupsByUserId>>> {
        @Override
        public void success(Result<List<FindGroupsByUserId>> data) {
            if (data.getStatus().equals("0000")) {
                List<FindGroupsByUserId> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    if (userNames.equals(result.get(i).getHxGroupId())) {
                        imIvQueryName.setText(result.get(i).getGroupName());
                        userNames = result.get(i).getHxGroupId();
                        groupImage = result.get(i).getGroupImage();
                        groupName = result.get(i).getGroupName();
                        groupId = result.get(i).getGroupId();
                    }
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
