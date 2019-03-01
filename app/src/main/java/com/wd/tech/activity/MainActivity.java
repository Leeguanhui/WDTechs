package com.wd.tech.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.MyListViewAdapter;
import com.wd.tech.activity.fragment.Fragment_Page_one;
import com.wd.tech.activity.fragment.Fragment_Page_three;
import com.wd.tech.activity.fragment.Fragment_Page_two;
import com.wd.tech.activity.secondactivity.AttenActivity;
import com.wd.tech.activity.secondactivity.CollectActivity;
import com.wd.tech.activity.secondactivity.IntegActivity;
import com.wd.tech.activity.secondactivity.InvitActivity;
import com.wd.tech.activity.secondactivity.NotificaActivity;
import com.wd.tech.activity.secondactivity.SettingActivity;
import com.wd.tech.activity.secondactivity.SignCalendarActivity;
import com.wd.tech.activity.secondactivity.TaskActivity;
import com.wd.tech.activity.secondactivity.UpdateMessageActivity;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.PersonallistBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ByIdUserInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends WDActivity {
    @BindView(R.id.radio)
    RadioGroup mRadio;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.one)
    RadioButton one;
    @BindView(R.id.two)
    RadioButton two;
    @BindView(R.id.three)
    RadioButton three;
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
    @BindView(R.id.login_bnt)
    LinearLayout login_bnt;
    @BindView(R.id.relat_one)
    RelativeLayout relat_one;
    @BindView(R.id.text_one)
    TextView text_one;
    @BindView(R.id.myheader)
    SimpleDraweeView myheader;
    @BindView(R.id.myname)
    TextView myname;
    @BindView(R.id.mysign)
    TextView mysign;
    private MyListViewAdapter myListViewAdapter;
    List<PersonallistBean> personallistBeanList = new ArrayList<>();
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private String nickName;
    private int comment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        closeSwipeBack();

        Intent intent = getIntent();
        comment = intent.getIntExtra("comment", 0);
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        myListViewAdapter = new MyListViewAdapter(this);
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment_page_one = new Fragment_Page_one();
        fragment_page_two = new Fragment_Page_two();
        fragment_page_three = new Fragment_Page_three();
        transaction.add(R.id.fragment, fragment_page_one);
        transaction.add(R.id.fragment, fragment_page_two);
        transaction.add(R.id.fragment, fragment_page_three);
        if (comment == 1) {
            transaction.show(fragment_page_three);
            transaction.hide(fragment_page_two);
            transaction.hide(fragment_page_one);
        } else {
            transaction.show(fragment_page_one);
            transaction.hide(fragment_page_two);
            transaction.hide(fragment_page_three);
        }
        transaction.commit();
        mRadio.check(mRadio.getChildAt(0).getId());
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
        left = (LinearLayout) findViewById(R.id.main_left_drawer_layout);
        right = (LinearLayout) findViewById(R.id.right_layout);
        main_drawer_layout.setScrimColor(Color.TRANSPARENT);//去除阴影
        left.measure(0, 0);
        final float width = left.getMeasuredWidth() * 0.2f;//获取布局宽度，并获得左移大小
        left.setTranslationX(-width);                 //底布局左移
        main_drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                left.setTranslationX(-width + width * slideOffset);               //底布局跟着移动
                right.setTranslationX(drawerView.getMeasuredWidth() * slideOffset);   //主界面布局移动，移动长度等于抽屉的移动长度
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
        //列表赋值
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    personallistBeanList.add(new PersonallistBean("我的收藏", R.drawable.my_icon_collect_n));
                    break;
                case 1:
                    personallistBeanList.add(new PersonallistBean("我的关注", R.drawable.my_icon_attention_n));
                    break;
                case 2:
                    personallistBeanList.add(new PersonallistBean("我的帖子", R.drawable.my_icon_card_n));
                    break;
                case 3:
                    personallistBeanList.add(new PersonallistBean("我的通知", R.drawable.my_icon_notice_n));
                    break;
                case 4:
                    personallistBeanList.add(new PersonallistBean("我的积分", R.drawable.my_icon_jifen_n));
                    break;
                case 5:
                    personallistBeanList.add(new PersonallistBean("我的任务", R.drawable.my_icon_task_n));
                    break;
                case 6:
                    personallistBeanList.add(new PersonallistBean("设置", R.drawable.my_icon_setting_n));
                    break;
            }
        }
        myListViewAdapter.addAll(personallistBeanList);
        listview.setAdapter(myListViewAdapter);
        //列表跳转
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //我的收藏
                        startActivity(new Intent(MainActivity.this, CollectActivity.class));
                        break;
                    case 1:
                        //我的关注
                        startActivity(new Intent(MainActivity.this, AttenActivity.class));
                        break;
                    case 2:
                        //我的帖子
                        startActivity(new Intent(MainActivity.this, InvitActivity.class));
                        break;
                    case 3:
                        //我的通知
                        startActivity(new Intent(MainActivity.this, NotificaActivity.class));
                        break;
                    case 4:
                        //我的积分
                        startActivity(new Intent(MainActivity.this, IntegActivity.class));
                        break;
                    case 5:
                        //我的任务
                        startActivity(new Intent(MainActivity.this, TaskActivity.class));
                        break;
                    case 6:
                        //设置
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }

            }
        });
        //跳转到登录注册
        login_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });
        //点击头像跳转
    }

    //签到
    @OnClick(R.id.sign_imag)
    public void sign_imag() {
        startActivity(new Intent(MainActivity.this, SignCalendarActivity.class));

    }

    @OnClick({R.id.myheader, R.id.myname})
    public void myheader() {

        Intent intent = new Intent(this, UpdateMessageActivity.class);
        intent.putExtra("name", nickName);
        startActivity(intent);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        LoginUserInfoBean userInfo = getUserInfo(this);
        if (userInfo != null) {
            relat_one.setVisibility(View.VISIBLE);
            text_one.setVisibility(View.VISIBLE);
            listview.setVisibility(View.VISIBLE);
            login_bnt.setVisibility(View.GONE);
            byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        } else {
            relat_one.setVisibility(View.GONE);
            text_one.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            login_bnt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 根据Id查询用户信息
     */
    private class ByIdUserResult implements ICoreInfe<Result> {


        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                ByIdUserInfoBean userInfoBean = (ByIdUserInfoBean) data.getResult();
                myheader.setImageURI(userInfoBean.getHeadPic());
                if (userInfoBean.getSignature() == null) {
                    mysign.setText("编辑个性签名");
                } else {
                    mysign.setText(userInfoBean.getSignature());
                    edit.putString("mysign", userInfoBean.getSignature());
                    edit.commit();
                }
                nickName = userInfoBean.getNickName();
                myname.setText(userInfoBean.getNickName());
            } else {
                Toast.makeText(MainActivity.this, "用户信息请求失败,请稍后重试。", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
