package com.wd.tech.fragment.addfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.AddGroupActivity;
import com.wd.tech.bean.FindGroupInfo;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zxk
 * on 2019/2/19 19:29
 * QQ:666666
 * Describe:
 */
public class Fragment2 extends WDFragment {

    @BindView(R.id.fragment2_search_edit)
    EditText fragment2SearchEdit;
    @BindView(R.id.search2_image)
    ImageView search2Image;
    @BindView(R.id.aaaaa)
    RelativeLayout aaaaaA;
    @BindView(R.id.ccc)
    RelativeLayout ccC;
    @BindView(R.id.image)
    SimpleDraweeView imagE;
    @BindView(R.id.you_text)
    TextView youText;
    @BindView(R.id.next)
    ImageView nexT;
    @BindView(R.id.bbb)
    RelativeLayout bbB;
    private FindGroupInfoPresenter findGroupInfoPresenter;
    private String sS;
    private String sessionId;
    private int userId;
    private int groupId;
    private String groupName;
    private String groupImage;
    private int currentCount;
    private int maxCount;
    private int ownerUid;
    private String description;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment2;
    }

    @Override
    protected void initView() {

        findGroupInfoPresenter = new FindGroupInfoPresenter(new FindGroup());
        LoginUserInfoBean infoBean = getUserInfo(getContext());
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
    }

    @OnClick({R.id.search2_image, R.id.next, R.id.bbb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search2_image:
                String s = fragment2SearchEdit.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    bbB.setVisibility(View.GONE);
                    return;
                }
                Integer integer = Integer.valueOf(s);

                findGroupInfoPresenter.request(userId, sessionId, integer);
                break;
            case R.id.next:
                AddQun();
                break;
            case R.id.bbb:
                AddQun();
                break;
        }
    }

    public void AddQun() {
        Intent intent = new Intent(getContext(), AddGroupActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("groupName", groupName);
        intent.putExtra("groupImage", groupImage);
        intent.putExtra("currentCount", currentCount);
        intent.putExtra("maxCount", maxCount);
        intent.putExtra("ownerUid", ownerUid);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    private class FindGroup implements ICoreInfe<Result<FindGroupInfo>> {


        @Override
        public void success(Result<FindGroupInfo> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                FindGroupInfo result = data.getResult();
                groupId = result.getGroupId();
                groupName = result.getGroupName();
                groupImage = result.getGroupImage();
                currentCount = result.getCurrentCount();
                maxCount = result.getMaxCount();
                ownerUid = result.getOwnerUid();
                description = result.getDescription();
                imagE.setImageURI(result.getGroupImage());
                youText.setText(result.getGroupName());
                bbB.setVisibility(View.VISIBLE);
                ccC.setVisibility(View.GONE);
            }
            if (data.getStatus().equals("1001")) {
                ccC.setVisibility(View.VISIBLE);
                bbB.setVisibility(View.GONE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
