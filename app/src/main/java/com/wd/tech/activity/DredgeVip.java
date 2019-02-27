package com.wd.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DredgeVip extends WDActivity {


    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.z)
    RelativeLayout z;
    @BindView(R.id.x)
    RelativeLayout x;
    @BindView(R.id.c)
    RelativeLayout c;
    @BindView(R.id.v)
    RelativeLayout v;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.chose_wx)
    RadioButton choseWx;
    @BindView(R.id.chose_zfb)
    RadioButton choseZfb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dredge_vip;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.z, R.id.x, R.id.c, R.id.v, R.id.chose_wx, R.id.chose_zfb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.z://点击年卡
                break;
            case R.id.x://点击季卡
                break;
            case R.id.c://点击年卡
                break;
            case R.id.v:
                break;
            case R.id.chose_wx:
                break;
            case R.id.chose_zfb:
                break;
        }
    }
}
