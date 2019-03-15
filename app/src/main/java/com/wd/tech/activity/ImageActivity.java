package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.MyImageAdapter;
import com.wd.tech.activity.view.PhotoViewPager;
import com.wd.tech.core.WDActivity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

public class ImageActivity extends WDActivity implements CustomAdapt {

    @BindView(R.id.iv)
    PhotoViewPager mViewPager;
    private MyImageAdapter adapter;
    public static final String TAG = ImageActivity.class.getSimpleName();
    private int currentPosition;
    @BindView(R.id.tv_image_count)
    TextView mTvImageCount;
    //    private TextView mTvSaveImage;
    private List<String> Urls;
    @BindView(R.id.relat)
    RelativeLayout relativeLayout;
    private List<LocalMedia> localMedia;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        Urls = intent.getStringArrayListExtra("image");
        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });
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
