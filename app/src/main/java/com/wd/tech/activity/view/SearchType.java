package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.ZXXRecyAdapter;
import com.wd.tech.activity.thirdlyactivity.SearchActivity;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ZxInformationPresenter;

import java.util.List;

import butterknife.BindView;

public class SearchType extends WDActivity {

    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.tname)
    TextView tname;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.xrecy)
    XRecyclerView xrecy;
    private ZxInformationPresenter zxInformationPresenter;
    private LoginUserInfoBean userInfo;
    private String sessionId;
    private int userId;
    private int num=1;
    private ZXXRecyAdapter zxxRecyAdapter;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_type;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        xrecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        zxxRecyAdapter = new ZXXRecyAdapter(this);
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        tname.setText(name);
        zxInformationPresenter = new ZxInformationPresenter(new Information());
        userInfo = getUserInfo(this);
        if (userInfo!=null){
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }
        zxInformationPresenter.request(userId,sessionId, id,num,10);
        xrecy.setAdapter(zxxRecyAdapter);
        zxxRecyAdapter.notifyDataSetChanged();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xrecy.setPullRefreshEnabled(true);
        xrecy.setLoadingMoreEnabled(true);
        xrecy.refresh();
        xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                num++;
                zxInformationPresenter.request(userId, sessionId,id,num,10);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                num++;
                zxInformationPresenter.request(userId, sessionId,id,num,10);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.loadMoreComplete();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchType.this,SearchActivity.class));
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    private class Information implements ICoreInfe<Result<List<InfoRecommecndListBean>>> {

        @Override
        public void success(Result<List<InfoRecommecndListBean>> data) {
            if (data.getStatus().equals("0000")){
                zxxRecyAdapter.setList(data.getResult());
                zxxRecyAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
