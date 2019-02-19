package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.NofitiRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class NotificaActivity extends WDActivity {
    @BindView(R.id.notifica_xrecycle)
    XRecyclerView notifica_xrecycle;
    private NofitiRecycleAdapter nofitiRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifica;
    }

    @Override
    protected void initView() {
        nofitiRecycleAdapter = new NofitiRecycleAdapter();
        notifica_xrecycle.setLayoutManager(new LinearLayoutManager(this));
        notifica_xrecycle.setAdapter(nofitiRecycleAdapter);
        notifica_xrecycle.setPullRefreshEnabled(true);
        notifica_xrecycle.setLoadingMoreEnabled(false);
    }

    @Override
    protected void destoryData() {

    }
}
