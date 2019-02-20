package com.wd.tech.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ImageActivity extends WDActivity implements CustomAdapt {

    @BindView(R.id.iv)
    ImageView simpleDraweeView;
    @BindView(R.id.relat)
    RelativeLayout relativeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Toast.makeText(this, "" + image, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(image).into(simpleDraweeView);
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

    @OnClick(R.id.relat)
    public void relat() {
        finish();
    }
}
