package com.wd.tech.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.NewsDetailsBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.NewsDetails_Presenter;

import java.text.SimpleDateFormat;

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
    TextView coment;
    private NewsDetails_Presenter newsDetails_presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        newsDetails_presenter = new NewsDetails_Presenter(new DetailsBack());
        newsDetails_presenter.request(0, "", id);
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
            //coment.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
            coment.setText(Html.fromHtml(result.getContent()));
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
