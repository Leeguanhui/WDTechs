package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.DetailsCommentAdapter;
import com.wd.tech.activity.adapter.DetailsMoreAdapter;
import com.wd.tech.activity.adapter.DetailsTypeAdapter;
import com.wd.tech.bean.DetailsCommentsBean;
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
    NoScrollWebView coment;
    @BindView(R.id.type)
    RecyclerView type;
    @BindView(R.id.more)
    RecyclerView more;
    @BindView(R.id.comments)
    RecyclerView comments;
    private NewsDetails_Presenter newsDetails_presenter;
    private DetailsTypeAdapter detailsTypeAdapter;
    private DetailsMoreAdapter detailsMoreAdapter;
    private DetailsCommentAdapter detailsCommentAdapter;
    private DetailsCommentsPresenter detailsCommentsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        detailsTypeAdapter = new DetailsTypeAdapter(this);
        detailsMoreAdapter = new DetailsMoreAdapter(this);
        detailsCommentAdapter = new DetailsCommentAdapter(this);
        detailsCommentsPresenter = new DetailsCommentsPresenter(new Comments());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        newsDetails_presenter = new NewsDetails_Presenter(new DetailsBack());
        newsDetails_presenter.request(0, "", id);
        detailsCommentsPresenter.request(0,"",id,1,10);
        comments.setLayoutManager(new LinearLayoutManager(NewsDetails.this,LinearLayoutManager.VERTICAL,false));
        comments.setAdapter(detailsCommentAdapter);
    }

    @Override
    protected void destoryData() {

    }

    private class DetailsBack implements ICoreInfe<Result<NewsDetailsBean>> {
        @Override
        public void success(Result<NewsDetailsBean> data) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            NewsDetailsBean result = data.getResult();
            title.setText(result.getTitle());
            simple.setImageURI(result.getThumbnail());

            String format = formatter.format(result.getReleaseTime());
            time.setText(format + "");
            writer.setText(result.getSource());
            coment.loadDataWithBaseURL(null, result.getContent(), "text/html", "utf-8", null);
            type.setLayoutManager(new LinearLayoutManager(NewsDetails.this,LinearLayoutManager.HORIZONTAL,false));
            detailsTypeAdapter.setList(result.getPlate());
            type.setAdapter(detailsTypeAdapter);
            more.setLayoutManager(new LinearLayoutManager(NewsDetails.this,LinearLayoutManager.HORIZONTAL,false));
            detailsMoreAdapter.setList(result.getInformationList());
            more.setAdapter(detailsMoreAdapter);

            //detailsCommentAdapter.setList(result.get)
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Comments implements ICoreInfe<Result<List<DetailsCommentsBean>>> {
        @Override
        public void success(Result<List<DetailsCommentsBean>> data) {
            detailsCommentAdapter.setList(data.getResult());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
