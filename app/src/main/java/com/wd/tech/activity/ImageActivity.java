package com.wd.tech.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.MyImageAdapter;
import com.wd.tech.activity.view.PhotoViewPager;
import com.wd.tech.core.WDActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
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
