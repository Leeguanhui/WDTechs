package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.DetailsCommentAdapter;
import com.wd.tech.activity.adapter.DetailsMoreAdapter;
import com.wd.tech.activity.adapter.DetailsTypeAdapter;
import com.wd.tech.bean.DetailsCommentsBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NewsDetailsBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DetailsCommentsPresenter;
import com.wd.tech.presenter.NewsDetails_Presenter;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetails extends WDActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.writer)
    TextView writer;
    @BindView(R.id.simple)
    SimpleDraweeView simple;
    @BindView(R.id.coment)
    WebView coment;
    @BindView(R.id.type)
    RecyclerView type;
    @BindView(R.id.more)
    RecyclerView more;
    @BindView(R.id.comments)
    RecyclerView comments;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.comment)
    TextView comment;
    @BindView(R.id.commentsnum)
    TextView commentsnum;
    @BindView(R.id.huifu)
    FrameLayout huifu;
    @BindView(R.id.likenum)
    TextView likenum;
    @BindView(R.id.great)
    FrameLayout great;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.shape)
    ImageView share;
    @BindView(R.id.fufei)
    Button fufei;
    @BindView(R.id.ralt)
    RelativeLayout ralt;
    @BindView(R.id.a)
    LinearLayout a;
    @BindView(R.id.b)
    LinearLayout b;
    private NewsDetails_Presenter newsDetails_presenter;
    private DetailsTypeAdapter detailsTypeAdapter;
    private DetailsMoreAdapter detailsMoreAdapter;
    private DetailsCommentAdapter detailsCommentAdapter;
    private DetailsCommentsPresenter detailsCommentsPresenter;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }


    @Override
    protected void initView() {
        LoginUserInfoBean userInfo = getUserInfo(this);
        if (userInfo != null) {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailsTypeAdapter = new DetailsTypeAdapter(this);
        detailsMoreAdapter = new DetailsMoreAdapter(this);
        detailsCommentAdapter = new DetailsCommentAdapter(this);
        detailsCommentsPresenter = new DetailsCommentsPresenter(new Comments());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Log.e("qwer",id+"");
        newsDetails_presenter = new NewsDetails_Presenter(new DetailsBack());
        newsDetails_presenter.request(userId, sessionId, id);
        detailsCommentsPresenter.request(userId, sessionId, id, 1, 10);
        comments.setLayoutManager(new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.VERTICAL, false));
        comments.setAdapter(detailsCommentAdapter);
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

    private class DetailsBack implements ICoreInfe<Result<NewsDetailsBean>> {
        @Override
        public void success(Result<NewsDetailsBean> data) {
            Log.e("qwerr",data.getMessage()+"");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            NewsDetailsBean result = data.getResult();
            title.setText(result.getTitle());
            simple.setImageURI(result.getThumbnail());
            String format = formatter.format(result.getReleaseTime());
            time.setText(format + "");
            writer.setText(result.getSource());
            if (data.getResult().getReadPower() == 2) {
                ralt.setVisibility(View.VISIBLE);
                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
            } else {
                WebSettings settings = coment.getSettings();
                settings.setTextZoom(250); // 通过百分比来设置文字的大小，默认值是100。
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                coment.loadDataWithBaseURL(null, result.getContent(), "text/html", "utf-8", null);
                type.setLayoutManager(new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.HORIZONTAL, false));
                detailsTypeAdapter.setList(result.getPlate());
                type.setAdapter(detailsTypeAdapter);
                more.setLayoutManager(new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.HORIZONTAL, false));
                detailsMoreAdapter.setList(result.getInformationList());
                more.setAdapter(detailsMoreAdapter);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Comments implements ICoreInfe<Result<List<DetailsCommentsBean>>> {
        @Override
        public void success(Result<List<DetailsCommentsBean>> data) {
            Log.e("qwerrr",data.getMessage()+"");
            detailsCommentAdapter.setList(data.getResult());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
