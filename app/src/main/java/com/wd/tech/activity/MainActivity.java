package com.wd.tech.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wd.tech.R;
import com.wd.tech.activity.fragment.Fragment_Page_one;
import com.wd.tech.activity.fragment.Fragment_Page_three;
import com.wd.tech.activity.fragment.Fragment_Page_two;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class MainActivity extends WDActivity {
    @BindView(R.id.radio)
    RadioGroup radio;
    private Fragment_Page_one fragment_page_one;
    private Fragment_Page_two fragment_page_two;
    private Fragment_Page_three fragment_page_three;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment_page_one = new Fragment_Page_one();
        fragment_page_two = new Fragment_Page_two();
        fragment_page_three = new Fragment_Page_three();
        transaction.add(R.id.fragment, fragment_page_one);
        transaction.add(R.id.fragment, fragment_page_two);
        transaction.add(R.id.fragment, fragment_page_three);
        transaction.show(fragment_page_one);
        transaction.hide(fragment_page_two);
        transaction.hide(fragment_page_three);
        transaction.commit();
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.one:
                        trans.show(fragment_page_one);
                        trans.hide(fragment_page_two);
                        trans.hide(fragment_page_three);
                        break;
                    case R.id.two:
                        trans.show(fragment_page_two);
                        trans.hide(fragment_page_one);
                        trans.hide(fragment_page_three);
                        break;
                    case R.id.three:
                        trans.show(fragment_page_three);
                        trans.hide(fragment_page_one);
                        trans.hide(fragment_page_two);
                        break;
                }
                trans.commit();
            }
        });
    }

    @Override
    protected void destoryData() {

    }
}
