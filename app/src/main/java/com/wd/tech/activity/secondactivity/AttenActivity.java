package com.wd.tech.activity.secondactivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.AttenRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.AttUserListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AttUserListPresenter;
import com.wd.tech.presenter.CanceFollowPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class AttenActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.xrecycle)
    SwipeMenuListView mXRecycle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private AttenRecycleAdapter attenRecycleAdapter;
    private AttUserListPresenter attUserListPresenter;
    int mPage = 1;
    int mCount = 1000;
    private LoginUserInfoBean userInfo;
    private Dialog dialog;
    private CanceFollowPresenter canceFollowPresenter;
    private List<AttUserListBean> resultResult;
    private Dialog showLoadDialog;
    private int mIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_atten;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = getUserInfo(this);
        canceFollowPresenter = new CanceFollowPresenter(new CanceResult());
        attUserListPresenter = new AttUserListPresenter(new AttUserResult());
        attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        attenRecycleAdapter = new AttenRecycleAdapter(this);
        mXRecycle.setAdapter(attenRecycleAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                attenRecycleAdapter.deleteAll();
                attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                attUserListPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
        dialog = CircularLoading.showLoadDialog(AttenActivity.this, "加载中...", true);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void create(SwipeMenu menu) {
                WindowManager m = AttenActivity.this.getWindowManager();
                Display d = m.getDefaultDisplay();
                //创建一个开放的item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                //设置item的背景
                openItem.setBackground(new ColorDrawable(Color.RED));
                //设置item的宽度
                int width = (int) (d.getWidth() * 0.2);
                openItem.setWidth(width);
                //设置item的标题
                openItem.setTitle("取消关注");
                openItem.setTitleColor(Color.WHITE);
                //设置item标题字体的大小
                openItem.setTitleSize(15);
                //添加到菜单中
                menu.addMenuItem(openItem);
            }
        };
//设置creator
        mXRecycle.setMenuCreator(creator);
        mXRecycle.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        List<AttUserListBean> list = attenRecycleAdapter.getList();
                        mIndex = position;
                        AttUserListBean attUserListBean = list.get(position);
                        canceFollowPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), attUserListBean.getFocusUid());
                        showLoadDialog = CircularLoading.showLoadDialog(AttenActivity.this, "", true);
                        break;
                }
                return false;
            }
        });
//设置滑动的方向
        mXRecycle.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);//左
    }

    @OnClick(R.id.back_btn)
    public void back_btn(){
        finish();
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


    /**
     * 关注用户列表
     */
    private class AttUserResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            resultResult = (List<AttUserListBean>) result.getResult();
            attenRecycleAdapter.addAll(resultResult);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 取消关注
     */
    private class CanceResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                attenRecycleAdapter.removePosition(mIndex);
                CircularLoading.closeDialog(showLoadDialog);
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
