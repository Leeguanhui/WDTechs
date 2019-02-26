package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ApplyAddGroupPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGroupActivity extends WDActivity {


    @BindView(R.id.find_group_details_back)
    ImageView findGroupDetailsBack;
    @BindView(R.id.find_group_details_name)
    TextView findGroupDetailsName;
    @BindView(R.id.find_group_details_icon)
    SimpleDraweeView findGroupDetailsIcon;
    @BindView(R.id.find_group_details_relative)
    RelativeLayout findGroupDetailsRelative;
    @BindView(R.id.find_group_details_num)
    TextView findGroupDetailsNum;
    @BindView(R.id.find_group_details_group)
    RelativeLayout findGroupDetailsGroup;
    @BindView(R.id.find_group_details_description)
    TextView findGroupDetailsDescription;
    @BindView(R.id.find_group_details_yes)
    Button findGroupDetailsYes;
    private int groupId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_group;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 0);
        String groupName = intent.getStringExtra("groupName");
        String groupImage = intent.getStringExtra("groupImage");
        String description = intent.getStringExtra("description");
        int currentCount = intent.getIntExtra("currentCount", 0);
//        int maxCount = intent.getIntExtra("maxCount", 0);
//        int ownerUid = intent.getIntExtra("ownerUid", 0);
        findGroupDetailsIcon.setImageURI(groupImage);
        findGroupDetailsName.setText(groupName);
        findGroupDetailsNum.setText("共" + currentCount + "人");
        findGroupDetailsDescription.setText(description);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.find_group_details_yes)
    public void onViewClicked() {
        Intent intent = new Intent(this, ApplyForActivity.class);
        intent.putExtra("groupId",groupId);
        startActivity(intent);
    }





    private class GroupAdd implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
