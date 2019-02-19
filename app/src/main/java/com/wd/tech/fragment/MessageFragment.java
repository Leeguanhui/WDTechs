package com.wd.tech.fragment;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.wd.tech.R;
import com.wd.tech.core.WDFragment;

import butterknife.BindView;

/**
 * Created by zxk
 * on 2019/2/18 19:47
 * QQ:666666
 * Describe:
 */
public class MessageFragment extends WDFragment {


    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.x_recyclerview)
    PullToRefreshScrollView xRecyclerview;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_layout;
    }

    @Override
    protected void initView() {
     xRecyclerview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
         @Override
         public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

         }
     });
    }

}
