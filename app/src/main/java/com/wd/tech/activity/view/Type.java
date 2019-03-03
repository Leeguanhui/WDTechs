package com.wd.tech.activity.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.TypeAdapter;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.TypeBean;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.TypePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Type extends WDActivity {

    @BindView(R.id.recy)
    RecyclerView recy;
    private TypePresenter typePresenter;
    private TypeAdapter typeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_type;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recy.setLayoutManager(new GridLayoutManager(this,2));
        typeAdapter = new TypeAdapter(this);
        typePresenter = new TypePresenter(new AllType());
        typePresenter.request();
        recy.setAdapter(typeAdapter);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class AllType implements ICoreInfe<Result<List<TypeBean>>> {
        @Override
        public void success(Result<List<TypeBean>> data) {
            typeAdapter.setList(data.getResult());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
