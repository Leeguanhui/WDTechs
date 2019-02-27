package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntergralExchange extends WDActivity {


    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.tname)
    TextView tname;
    @BindView(R.id.a)
    RelativeLayout a;
    @BindView(R.id.simple)
    SimpleDraweeView simple;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.writer)
    TextView writer;
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.sharewith)
    ImageView sharewith;
    @BindView(R.id.likenum)
    TextView likenum;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.b)
    RelativeLayout b;
    @BindView(R.id.need)
    TextView need;
    @BindView(R.id.mean)
    TextView mean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intergral_exchange;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int integralCost = intent.getIntExtra("integralCost", 0);
        int id = intent.getIntExtra("id", 0);
        int praise = intent.getIntExtra("praise", 0);
        String thumbnail = intent.getStringExtra("thumbnail");
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        long releaseTime = intent.getLongExtra("releaseTime", 0);
        mean.setText(integralCost+"");
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
}
