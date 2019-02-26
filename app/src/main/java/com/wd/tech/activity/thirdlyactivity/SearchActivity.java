package com.wd.tech.activity.thirdlyactivity;


import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.SearchRecycleAdapter;
import com.wd.tech.activity.view.FlowLayout;
import com.wd.tech.bean.ByTitleBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindByTitlePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends WDActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.call_btn)
    TextView call_btn;
    @BindView(R.id.flow)
    FlowLayout flow;
    private FindByTitlePresenter findByTitlePresenter;
    private int mPage = 1;
    private int mContent = 10000000;
    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;
    private SearchRecycleAdapter searchRecycleAdapter;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick(R.id.search_image)
    public void search_image() {
        content = search_edit.getText().toString();
        searchRecycleAdapter.removeAll();
        findByTitlePresenter.request(content, mPage, mContent);
    }

    @OnClick(R.id.call_btn)
    public void call_btn() {
        finish();
    }


    @Override
    protected void initView() {
        findByTitlePresenter = new FindByTitlePresenter(new FindByTitleResult());
        xrecycle.setLayoutManager(new LinearLayoutManager(this));
        searchRecycleAdapter = new SearchRecycleAdapter();
        xrecycle.setAdapter(searchRecycleAdapter);
        xrecycle.setLoadingListener(this);
        xrecycle.setLoadingMoreEnabled(false);
        xrecycle.setPullRefreshEnabled(true);
        search_edit.addTextChangedListener(new MyTextWatcher());
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        searchRecycleAdapter.removeAll();
        findByTitlePresenter.request(content, mPage, mContent);
    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 根据标题查询
     */
    private class FindByTitleResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            List<ByTitleBean> byTitleBeans = (List<ByTitleBean>) result.getResult();
            if (byTitleBeans.size() == 0) {
                line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                xrecycle.setVisibility(View.GONE);
                return;
            }
            searchRecycleAdapter.addAll(byTitleBeans);
            line1.setVisibility(View.GONE);
            xrecycle.setVisibility(View.VISIBLE);
            xrecycle.refreshComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    public class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (search_edit.getText().toString().length() == 0) {
                line2.setVisibility(View.GONE);
                line1.setVisibility(View.VISIBLE);
                xrecycle.setVisibility(View.GONE);
            }
        }
    }
}
