package com.wd.tech.activity.thirdlyactivity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.adapter.SearchRecycleAdapter;
import com.wd.tech.activity.view.FlowLayout;
import com.wd.tech.bean.ByTitleBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindByTitlePresenter;
import com.wd.tech.presenter.FindTitlePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends WDActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.line1)
    LinearLayout mLine1;
    @BindView(R.id.line2)
    LinearLayout mLine2;
    @BindView(R.id.search_edit)
    EditText mSearchedit;
    @BindView(R.id.call_btn)
    TextView mCallbtn;
    @BindView(R.id.flow)
    FlowLayout mFlow;
    private FindByTitlePresenter findByTitlePresenter;
    private int mPage = 1;
    private int mContent = 10000000;
    @BindView(R.id.xrecycle)
    XRecyclerView mXrecycle;
    private SearchRecycleAdapter searchRecycleAdapter;
    private String content;
    private FindTitlePresenter findTitlePresenter;
    private InputMethodManager systemService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick(R.id.search_image)
    public void search_image() {
        content = mSearchedit.getText().toString();
        searchRecycleAdapter.removeAll();
        findByTitlePresenter.request(content, mPage, mContent);
    }

    @OnClick(R.id.call_btn)
    public void call_btn() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemService = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }



    @Override
    protected void initView(Bundle savedInstanceState) {
        findTitlePresenter = new FindTitlePresenter(new TitleCall());
        findByTitlePresenter = new FindByTitlePresenter(new FindByTitleResult());
        mXrecycle.setLayoutManager(new LinearLayoutManager(this));
        searchRecycleAdapter = new SearchRecycleAdapter(this);
        mXrecycle.setAdapter(searchRecycleAdapter);
        mXrecycle.setLoadingListener(this);
        mXrecycle.setLoadingMoreEnabled(false);
        mXrecycle.setPullRefreshEnabled(true);
        mSearchedit.addTextChangedListener(new MyTextWatcher());

        mSearchedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                content = mSearchedit.getText().toString();
                searchRecycleAdapter.removeAll();
                findByTitlePresenter.request(content, mPage, mContent);
                return true;
            }
        });
        mSearchedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager systemService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (systemService != null) {
                        systemService.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });

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
            systemService.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
            List<ByTitleBean> byTitleBeans = (List<ByTitleBean>) result.getResult();
            if (byTitleBeans.size() == 0) {

                findTitlePresenter.request(content, mPage, mContent);
                /*line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                xrecycle.setVisibility(View.GONE);*/
                return;
            }
            searchRecycleAdapter.addAll(byTitleBeans);
            mLine1.setVisibility(View.GONE);
            mXrecycle.setVisibility(View.VISIBLE);
            mXrecycle.refreshComplete();
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
            if (mSearchedit.getText().toString().length() == 0) {
                mLine2.setVisibility(View.GONE);
                mLine1.setVisibility(View.VISIBLE);
                mXrecycle.setVisibility(View.GONE);
            }
        }
    }

    private class TitleCall implements ICoreInfe<Result<List<ByTitleBean>>> {
        @Override
        public void success(Result<List<ByTitleBean>> data) {
            List<ByTitleBean> byTitleBeans = (List<ByTitleBean>) data.getResult();
            if (byTitleBeans.size() == 0) {
                mLine2.setVisibility(View.VISIBLE);
                mLine1.setVisibility(View.GONE);
                mXrecycle.setVisibility(View.GONE);
                return;
            }
            searchRecycleAdapter.addAll(byTitleBeans);
            mLine1.setVisibility(View.GONE);
            mXrecycle.setVisibility(View.VISIBLE);
            mXrecycle.refreshComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
