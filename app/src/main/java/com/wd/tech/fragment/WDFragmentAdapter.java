package com.wd.tech.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zxk
 * on 2019/2/18 20:13
 * QQ:666666
 * Describe:
 */
public class WDFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public WDFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
