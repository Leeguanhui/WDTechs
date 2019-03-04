package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FindGroupsByCreate;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ApplyAddGroupPresenter;
import com.wd.tech.presenter.FindGroupsByCreatePresenter;
import com.wd.tech.presenter.RetreatPresenter;
import com.wd.tech.presenter.WhetherInGroupPresenter;

import java.util.List;

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
    @BindView(R.id.groupdetails_btntui)
    Button groupdetailsBtntui;
    @BindView(R.id.groupdetails_btnjie)
    Button groupdetailsBtnjie;
    private int groupId;
    private WhetherInGroupPresenter whetherInGroupPresenter;
    private boolean flag = false;
    private FindGroupsByCreatePresenter findGroupsByCreatePresenter;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean infoBean;
    private RetreatPresenter retreatPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_group;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        retreatPresenter = new RetreatPresenter(new Retreat());
        whetherInGroupPresenter = new WhetherInGroupPresenter(new Whether());
        findGroupsByCreatePresenter = new FindGroupsByCreatePresenter(new Create());
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }


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
        findGroupsByCreatePresenter.request(userId,sessionId);
        whetherInGroupPresenter.request(userId, sessionId,groupId);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.find_group_details_yes,R.id.groupdetails_btntui})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.find_group_details_yes:
                Intent intent = new Intent(this, ApplyForActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
                break;
            case R.id.groupdetails_btntui:
                 retreatPresenter.request(userId,sessionId,groupId);
                break;
        }

    }





    private class GroupAdd implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class Create implements ICoreInfe<Result<List<FindGroupsByCreate>>>{

        @Override
        public void success(Result<List<FindGroupsByCreate>> result) {
            Toast.makeText(AddGroupActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")){
                List<FindGroupsByCreate> result1 = result.getResult();
                for (int i = 0; i < result1.size(); i++) {
                    if(groupId==result1.get(i).getGroupId()){
                        flag = true;
                        return;
                    }
                }
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class Whether implements ICoreInfe<Result> {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000")){
                if(result.getFlag()==2){
                    groupdetailsBtntui.setVisibility(View.GONE);
                    groupdetailsBtnjie.setVisibility(View.GONE);
                    findGroupDetailsYes.setVisibility(View.VISIBLE);
                }else if(result.getFlag()== 1){
                    if(flag){
                        groupdetailsBtntui.setVisibility(View.GONE);
                        groupdetailsBtnjie.setVisibility(View.VISIBLE);
                        findGroupDetailsYes.setVisibility(View.GONE);
                    }else {
                        groupdetailsBtntui.setVisibility(View.VISIBLE);
                        groupdetailsBtnjie.setVisibility(View.GONE);
                        findGroupDetailsYes.setVisibility(View.GONE);
                    }

                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Retreat implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
               if (data.getStatus().equals("0000")){
                   Toast.makeText(AddGroupActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                   findGroupsByCreatePresenter.request(userId,sessionId);
                   finish();
               }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
