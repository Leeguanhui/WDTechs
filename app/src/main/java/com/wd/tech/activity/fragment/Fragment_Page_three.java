package com.wd.tech.activity.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.ReleasePostActivity;
import com.wd.tech.activity.adapter.CommunityListAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddCommunityCommentPresenter;
import com.wd.tech.presenter.AddCommunityGreatPresenter;
import com.wd.tech.presenter.CommunityListPresenter;
import com.wd.tech.presenter.DoTheTaskPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_three extends WDFragment implements CustomAdapt {

    private CommunityListPresenter communityListPresenter;
    @BindView(R.id.communitylist_recycler)
    RecyclerView recyclerView;
    private CommunityListAdapter communityListAdapter;
    private int page = 1;
    private String sessionId;
    private int userId;
    private LoginUserInfoBean userInfo;
    private AddCommunityGreatPresenter addCommunityGreatPresenter;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout refreshLayout;
    private Dialog dialog;
    private AddCommunityCommentPresenter addCommunityCommentPresenter;
    private View pl;
    private TextView button;
    private EditText editText;
    private Dialog bottomDialog;
    private int ids;
    private DoTheTaskPresenter doTheTaskPresenter;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_three;
    }

    @Override
    protected void initView() {
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheResult());
        AutoSizeConfig.getInstance().setCustomFragment(true);
        if (userInfo == null) {
            userId = 0;
            sessionId = "0";
        } else {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }

        //dialog
        bottomDialog = new Dialog(getContext(), R.style.BottomDialog);

        communityListPresenter = new CommunityListPresenter(new CommunityList());
        communityListPresenter.request(userId, sessionId, 1, 1000);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        communityListAdapter = new CommunityListAdapter(getContext());

        recyclerView.setAdapter(communityListAdapter);

        //点赞
        addCommunityGreatPresenter = new AddCommunityGreatPresenter(new AddCommunityGreat());

        communityListAdapter.setAddCommunityGreat(new CommunityListAdapter.addCommunityGreat() {
            @Override
            public void addCommunityGreat(int id, ImageView addCommunityGreat, String trim, TextView community_praise, CommunityListBean communityListBean) {
                addCommunityGreatPresenter.request(userId, sessionId, id);
                addCommunityGreat.setImageResource(R.drawable.common_icon_p);
                int a = Integer.parseInt(trim) + 1;
                communityListBean.setPraise(a);
                community_praise.setText(String.valueOf(communityListBean.getPraise()));
            }

            //评论
            @Override
            public void addCommunityComment(int id, ImageView communityIv) {
                ids = id;
                show(pl);
            }
        });

        //评论
        addCommunityCommentPresenter = new AddCommunityCommentPresenter(new AddCommunityComment());

        pl = LayoutInflater.from(getContext()).inflate(R.layout.comment_popupwindow, null);
        editText = pl.findViewById(R.id.et_discuss);
        button = pl.findViewById(R.id.tv_confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = editText.getText().toString().trim();
                dialog = CircularLoading.showLoadDialog(getContext(), "加载中...", true);
                addCommunityCommentPresenter.request(userId, sessionId, ids, trim);
            }
        });

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
//        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        //上下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                communityListAdapter.removeAll();

                communityListAdapter.notifyDataSetChanged();
                communityListPresenter.request(userId, sessionId, page, 1000);
            }
        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                page++;
//                communityListAdapter.removeAll();
//                communityListAdapter.notifyDataSetChanged();
//                communityListPresenter.request(userId, sessionId, page, 1000);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfo = getUserInfo(getContext());
        if (userInfo == null) {
            userId = 0;
            sessionId = "0";
        } else {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }

        dialog = CircularLoading.showLoadDialog(getContext(), "加载中...", true);

        communityListAdapter.removeAll();
        communityListPresenter.request(userId, sessionId, 1, 1000);
    }

    private class CommunityList implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            List<CommunityListBean> circleBeans = (List<CommunityListBean>) data.getResult();
            communityListAdapter.removeAll();
            communityListAdapter.addItem(circleBeans);
            communityListAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();//结束刷新
            refreshLayout.finishLoadmore();//结束加载
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 发布帖子
     */
    @OnClick(R.id.add_community)
    public void add_community() {
        if (userInfo != null) {
            startActivity(new Intent(getContext(), ReleasePostActivity.class));
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
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
            communityListAdapter.removeAll();
            communityListPresenter.request(userId, sessionId, 1, 1000);
            doTheTaskPresenter.request(userId,sessionId,1002);
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

    private class DoTheResult implements ICoreInfe {
        @Override
        public void success(Object data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
