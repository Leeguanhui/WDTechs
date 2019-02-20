package com.wd.tech.fragment.addfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.wd.tech.R;
import com.wd.tech.core.WDFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zxk
 * on 2019/2/19 19:27
 * QQ:666666
 * Describe:
 */
public class Fragment1 extends WDFragment {
    @BindView(R.id.fragment1_search_edit)
    EditText fragment1SearchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.list_view)
    ListView listView;
    Unbinder unbinder;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment1;
    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.search_image)
    public void onViewClicked() {

    }
}
