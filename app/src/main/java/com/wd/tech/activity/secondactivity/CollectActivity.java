package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
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

public class CollectActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.collect_xrecycle)
    XRecyclerView mCollectRecycle;
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
    protected void initView() {
        cancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollecResult());
        collectRecycleAdapter = new CollectRecycleAdapter();
        mCollectRecycle.setLayoutManager(new LinearLayoutManager(this));
        mCollectRecycle.setAdapter(collectRecycleAdapter);
        mCollectRecycle.setLoadingMoreEnabled(true);
        mCollectRecycle.setPullRefreshEnabled(true);
        mCollectRecycle.setLoadingListener(this);
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
        String substring = checkId.substring(0, checkId.length() - 1);
        cancelCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), substring);
        dialog = CircularLoading.showLoadDialog(CollectActivity.this, "加载中...", true);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        collectRecycleAdapter.deleteAll();
        findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        findAllCollectionPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), mPage, mCount);
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
            mCollectRecycle.refreshComplete();
            mCollectRecycle.loadMoreComplete();
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadmore();
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
