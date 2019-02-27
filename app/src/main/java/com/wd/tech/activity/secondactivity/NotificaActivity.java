package com.wd.tech.activity.secondactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.NofitiRecycleAdapter;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class NotificaActivity extends WDActivity {
    @BindView(R.id.notifica_xrecycle)
    XRecyclerView mNotificaxrecycle;
    private NofitiRecycleAdapter mNofitiRecycleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifica;
    }

    @Override
    protected void initView() {
        mNofitiRecycleAdapter = new NofitiRecycleAdapter();
        mNotificaxrecycle.setLayoutManager(new LinearLayoutManager(this));
        mNotificaxrecycle.setAdapter(mNofitiRecycleAdapter);
        mNotificaxrecycle.setPullRefreshEnabled(true);
        mNotificaxrecycle.setLoadingMoreEnabled(false);
    }

    @Override
    protected void destoryData() {

    }
}
