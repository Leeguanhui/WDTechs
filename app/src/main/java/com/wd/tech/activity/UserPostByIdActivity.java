package com.wd.tech.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.UserPostByIdAdapter;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.CommunityUserVoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.QueryFriendInformationBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddCommunityCommentPresenter;
import com.wd.tech.presenter.AddCommunityGreatPresenter;
import com.wd.tech.presenter.AddFollowPresenter;
import com.wd.tech.presenter.CanceFollowPresenter;
import com.wd.tech.presenter.QueryFriendInformationPresenter;
import com.wd.tech.presenter.UserPostByIdPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    private View pl;
    private Dialog bottomDialog;
    private Dialog dialog;
    private AddCommunityCommentPresenter addCommunityCommentPresenter;
    private TextView button;
    private EditText editText;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    private int userIds;
    private AddFollowPresenter addFollowPresenter;
    @BindView(R.id.attention)
    Button attention;
    @BindView(R.id.friends)
    Button friends;
    private QueryFriendInformationPresenter queryFriendInformationPresenter;
    private String phone;
    private long birthday;
    private String email;
    private String headPic;
    private int integral;
    private String nickName;
    private int sex;
    private int whetherVip;
    private String signature1;
    private CanceFollowPresenter canceFollowPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_post_by_id;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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

            //评论
            @Override
            public void addCommunityComment(int id) {
                show(pl);
            }
        });

        //dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);

        //评论
        addCommunityCommentPresenter = new AddCommunityCommentPresenter(new AddCommunityComment());

        pl = LayoutInflater.from(this).inflate(R.layout.comment_popupwindow, null);
        editText = pl.findViewById(R.id.et_discuss);
        button = pl.findViewById(R.id.tv_confirm);

        addFollowPresenter = new AddFollowPresenter(new AddFollow());
        queryFriendInformationPresenter = new QueryFriendInformationPresenter(new QueryFriendInformation());

        canceFollowPresenter = new CanceFollowPresenter(new CanceFollow());
    }

    @OnClick(R.id.more)
    public void more() {
        more.setVisibility(View.GONE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationX", 0f, -400f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        queryFriendInformationPresenter.request(userId, sessionId, userIds);
    }

    //加好友
    @OnClick(R.id.friends)
    public void friends() {
        if (friends.getText().toString().trim().equals("已添加")) {
            Toast.makeText(this, "已经添加过该好友了，不能重复添加", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, AddFriendlyActivity.class);
        intent.putExtra("userid1", userIds);
        intent.putExtra("sss", phone);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("nickName", nickName);
        intent.putExtra("sex", sex);
        intent.putExtra("headPic", headPic);
        intent.putExtra("integral", integral);
        intent.putExtra("signature", signature1);
        intent.putExtra("birthday", birthday);
        intent.putExtra("whetherVip", whetherVip);
        startActivity(intent);
    }

    //关注
    @OnClick(R.id.attention)
    public void attention() {
        if (attention.getText().toString().trim().equals("已关注")) {
            canceFollowPresenter.request(userId, sessionId, userIds);
        } else {
            addFollowPresenter.request(userId, sessionId, userIds);
        }
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
                if (communityUserVo.getWhetherFollow() == 1) {
                    attention.setText("已关注");
                }
                if (communityUserVo.getWhetherMyFriend() == 1) {
                    friends.setText("已添加");
                }
                userIds = communityUserVo.getUserId();
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

    private class AddFollow implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            attention.setText("已关注");
            Toast.makeText(UserPostByIdActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 查询好友信息
     */
    private class QueryFriendInformation implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            QueryFriendInformationBean result = (QueryFriendInformationBean) data.getResult();
            phone = result.getPhone();
            birthday = result.getBirthday();
            email = result.getEmail();
            headPic = result.getHeadPic();
            integral = result.getIntegral();
            nickName = result.getNickName();
            sex = result.getSex();
            whetherVip = result.getWhetherVip();
            signature1 = result.getSignature();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CanceFollow implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            attention.setText("+关注");
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
