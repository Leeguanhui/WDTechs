package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.CommunityListAdapter;
import com.wd.tech.activity.adapter.UserPostByIdAdapter;
import com.wd.tech.activity.fragment.Fragment_Page_three;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.CommunityUserVoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddCommunityCommentPresenter;
import com.wd.tech.presenter.AddCommunityGreatPresenter;
import com.wd.tech.presenter.UserPostByIdPresenter;

import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

public class UserPostByIdActivity extends WDActivity implements CustomAdapt {

    private UserPostByIdPresenter userPostByIdPresenter;
    private LoginUserInfoBean userInfo;
    private int userId;
    private String sessionId;
    @BindView(R.id.pic)
    SimpleDraweeView pic;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.image_bg)
    ImageView imageBg;
    private UserPostByIdAdapter userPostByIdAdapter;
    @BindView(R.id.xrecycler)
    RecyclerView recyclerView;
    private AddCommunityGreatPresenter addCommunityGreatPresenter;
    private int ids;
    private View pl;
    private Dialog bottomDialog;
    private Dialog dialog;
    private AddCommunityCommentPresenter addCommunityCommentPresenter;
    private TextView button;
    private EditText editText;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_post_by_id;
    }

    @Override
    protected void initView() {

        userInfo = getUserInfo(this);
        if (userInfo != null) {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        userPostByIdPresenter = new UserPostByIdPresenter(new UserPostById());
        userPostByIdPresenter.request(userId, sessionId, id, 1, 10);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        userPostByIdAdapter = new UserPostByIdAdapter(this);
        recyclerView.setAdapter(userPostByIdAdapter);

        //点赞
        addCommunityGreatPresenter = new AddCommunityGreatPresenter(new AddCommunityGreat());

        userPostByIdAdapter.setAddCommunityGreat(new UserPostByIdAdapter.addCommunityGreat() {
            @Override
            public void addCommunityGreat(int id, ImageView addCommunityGreat, String trim, TextView community_praise, CommunityCommentVoListBean communityListBean) {
                addCommunityGreatPresenter.request(userId, sessionId, id);
                addCommunityGreat.setImageResource(R.drawable.common_icon_p);
                int a = Integer.parseInt(trim) + 1;
                communityListBean.setPraise(a);
                community_praise.setText(String.valueOf(communityListBean.getPraise()));
            }
        });

        //dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);

        userPostByIdAdapter.setAddCommunityComment(new UserPostByIdAdapter.AddCommunityComment() {
            @Override
            public void addCommunityComment(int id) {
                ids = id;
                show(pl);
            }
        });

        //评论
        addCommunityCommentPresenter = new AddCommunityCommentPresenter(new AddCommunityComment());

        pl = LayoutInflater.from(this).inflate(R.layout.comment_popupwindow, null);
        editText = pl.findViewById(R.id.et_discuss);
        button = pl.findViewById(R.id.tv_confirm);
        //评论
        userPostByIdAdapter.setAddCommunityComment(new UserPostByIdAdapter.AddCommunityComment() {
            @Override
            public void addCommunityComment(int id) {
                ids = id;
                show(pl);
            }
        });

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class UserPostById implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            List<CommunityListBean> result = (List<CommunityListBean>) data.getResult();
            for (int i = 0; i < result.size(); i++) {
                CommunityUserVoBean communityUserVo = result.get(i).getCommunityUserVo();
                pic.setImageURI(Uri.parse(communityUserVo.getHeadPic()));
                name.setText(communityUserVo.getNickName());
                signature.setText(communityUserVo.getSignature());
                Glide.with(UserPostByIdActivity.this).load(communityUserVo.getHeadPic()).into(imageBg);
                List<CommunityCommentVoListBean> communityUserPostVoList = result.get(i).getCommunityUserPostVoList();
                userPostByIdAdapter.addItem(communityUserPostVoList);
                userPostByIdAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //点赞
    private class AddCommunityGreat implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //评论
    private class AddCommunityComment implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            bottomDialog.dismiss();
            dialog.dismiss();
            editText.setText(null);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //dialog
    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

}
