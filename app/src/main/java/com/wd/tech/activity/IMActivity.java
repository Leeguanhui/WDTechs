package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wd.tech.R;
import com.wd.tech.activity.secondactivity.SettingActivity;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.core.WDActivity;

import java.io.Serializable;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.hx_ok, chatFragment).commit();
        Intent intent = getIntent();
        friendInfoList = (FriendInfoList) intent.getSerializableExtra("friendInfoList");
        userId = intent.getIntExtra("userId", 0);
        sessionId = intent.getStringExtra("sessionId");
        imIvQueryName.setText(friendInfoList.getRemarkName());
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
                intent.putExtra("friendInfoList", friendInfoList);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (  resultCode == RESULT_OK) {
            String sss = data.getStringExtra("remarkName");
            imIvQueryName.setText(sss);
        }

    }
}
