package com.wd.tech.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.WDFragmentAdapter;
import com.wd.tech.core.WDFragment;
import com.wd.tech.fragment.LinkmanFragment;
import com.wd.tech.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_two extends WDFragment {


    @BindView(R.id.message)
    RadioButton message;
    @BindView(R.id.linkman_contacts)
    RadioButton linkmanContacts;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    private List<Fragment> list;
    private WDFragmentAdapter wdFragmentAdapter;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_two;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        list.add(new MessageFragment());
        list.add(new LinkmanFragment());
        wdFragmentAdapter = new WDFragmentAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(wdFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    @OnClick({R.id.message, R.id.linkman_contacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message:
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.linkman_contacts:
                viewPager.setCurrentItem(1,false);
                break;
        }
    }
}
