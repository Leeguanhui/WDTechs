package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.LoginActivity;
import com.wd.tech.activity.adapter.CollectRecycleAdapter;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.FindCollectBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.FindAllCollectionPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class CollectActivity extends WDActivity implements CustomAdapt {

    @BindView(R.id.collect_xrecycle)
    RecyclerView mCollectRecycle;
    private CollectRecycleAdapter collectRecycleAdapter;
    private FindAllCollectionPresenter findAllCollectionPresenter;
    private int mPage = 1;
    private int mCount = 5;
    private LoginUserInfoBean userInfo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.delete_image)
    ImageView mDeleteimage;
    @BindView(R.id.ok_btn)
    TextView mOkbtn;
    private CancelCollectionPresenter cancelCollectionPresenter;
    private Dialog dialog;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPage = 1;
            collectRecycleAdapter.deleteAll();
            findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        dialog = CircularLoading.showLoadDialog(CollectActivity.this, "加载中...", true);
        cancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollecResult());
        collectRecycleAdapter = new CollectRecycleAdapter();
        mCollectRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCollectRecycle.setAdapter(collectRecycleAdapter);
        findAllCollectionPresenter = new FindAllCollectionPresenter(new FindColleResult());
        userInfo = getUserInfo(this);
        findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                collectRecycleAdapter.deleteAll();
                findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    //删除按钮
    @OnClick(R.id.delete_image)
    public void delete_image() {
        collectRecycleAdapter.showRadio();
        mOkbtn.setVisibility(View.VISIBLE);
        mDeleteimage.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        collectRecycleAdapter.hideRadio();
        mDeleteimage.setVisibility(View.VISIBLE);
        mOkbtn.setVisibility(View.GONE);
        String checkId = collectRecycleAdapter.getCheckId();
        if (checkId.equals("") || checkId == null) {
            return;
        }
        String substring = checkId.substring(0, checkId.length() - 1);
        cancelCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), substring);
        dialog = CircularLoading.showLoadDialog(CollectActivity.this, "加载中...", true);
    }
    @OnClick(R.id.back_imag)
    public void back_imag(){
        finish();
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
     * 收藏列表
     */
    private class FindColleResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            List<FindCollectBean> findColleResults = (List<FindCollectBean>) result.getResult();
            collectRecycleAdapter.addAll(findColleResults);
            collectRecycleAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadmore();
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 删除收藏
     */
    private class CancelCollecResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            CircularLoading.closeDialog(dialog);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = handler.obtainMessage();
                    handler.sendMessage(message);
                }
            }).start();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
