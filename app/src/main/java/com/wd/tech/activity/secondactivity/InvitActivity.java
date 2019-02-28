package com.wd.tech.activity.secondactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class InvitActivity extends WDActivity implements CustomAdapt {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_invit;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
