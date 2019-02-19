package com.wd.tech.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.wd.tech.R;
import com.wd.tech.core.WDFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zxk
 * on 2019/2/18 19:47
 * QQ:666666
 * Describe:
 */
public class LinkmanFragment extends WDFragment {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.x_recyclerview)
    PullToRefreshScrollView pullToRefreshScrollView;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.linkman_layout;
    }

    @Override
    protected void initView() {
      pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
      pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
          @Override
          public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
              pullToRefreshScrollView.onRefreshComplete();
          }
      });
    }
}
