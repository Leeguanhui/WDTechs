package com.wd.tech.activity;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.MyListViewAdapter;
import com.wd.tech.activity.fragment.Fragment_Page_one;
import com.wd.tech.activity.fragment.Fragment_Page_three;
import com.wd.tech.activity.fragment.Fragment_Page_two;
import com.wd.tech.bean.PersonallistBean;
import com.wd.tech.core.WDActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends WDActivity {
    @BindView(R.id.radio)
    RadioGroup mRadio;
    @BindView(R.id.listview)
    ListView listview;
    private Fragment_Page_one fragment_page_one;
    private Fragment_Page_two fragment_page_two;
    private Fragment_Page_three fragment_page_three;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout main_drawer_layout;
    private boolean isDrawer;
    @BindView(R.id.main_left_drawer_layout)
    LinearLayout left;
    @BindView(R.id.right_layout)
    LinearLayout right;
    private MyListViewAdapter myListViewAdapter;
    List<PersonallistBean> personallistBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        myListViewAdapter = new MyListViewAdapter(this);
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
        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        main_drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    personallistBeanList.add(new PersonallistBean("我的收藏", R.drawable.my_icon_attention_n));
                    break;
                case 1:
                    personallistBeanList.add(new PersonallistBean("我的关注", R.drawable.my_icon_attention_n));
                    break;
                case 2:
                    personallistBeanList.add(new PersonallistBean("我的帖子", R.drawable.my_icon_attention_n));
                    break;
                case 3:
                    personallistBeanList.add(new PersonallistBean("我的通知", R.drawable.my_icon_attention_n));
                    break;
                case 4:
                    personallistBeanList.add(new PersonallistBean("我的积分", R.drawable.my_icon_attention_n));
                    break;
                case 5:
                    personallistBeanList.add(new PersonallistBean("我的任务", R.drawable.my_icon_attention_n));
                    break;
                case 6:
                    personallistBeanList.add(new PersonallistBean("设置", R.drawable.my_icon_attention_n));
                    break;
            }
        }
        myListViewAdapter.addAll(personallistBeanList);
        listview.setAdapter(myListViewAdapter);
    }

    @Override
    protected void destoryData() {

    }
}
