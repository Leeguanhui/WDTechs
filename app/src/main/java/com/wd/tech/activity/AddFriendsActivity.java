package com.wd.tech.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.AddYouAdapter;
import com.wd.tech.core.WDActivity;
import com.wd.tech.fragment.addfragment.Fragment1;
import com.wd.tech.fragment.addfragment.Fragment2;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendsActivity extends WDActivity {

    @BindView(R.id.back)
    ImageView backI;
    @BindView(R.id.course_search_tablayout)
    TabLayout courseSearchTablayout;
    @BindView(R.id.search_pager)
    ViewPager searchPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friends;
    }

    @Override
    protected void initView() {
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        list.add("找人");
        list.add("找群");
        courseSearchTablayout.setupWithViewPager(searchPager);
        searchPager.setAdapter(new AddYouAdapter(getSupportFragmentManager(), mFragments, list));

    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
