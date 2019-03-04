package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.secondactivity.TaskActivity;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.UserIntegralBean;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.NewsExChangePresenter;
import com.wd.tech.presenter.UserIntegral;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.exchange)
    Button exchange;
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
    private UserIntegral userIntegral;
    private int amount;
    private int integralCost;
    private NewsExChangePresenter newsExChangePresenter;
    private int userId;
    private String sessionId;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intergral_exchange;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        newsExChangePresenter = new NewsExChangePresenter(new Change());
        userIntegral = new UserIntegral(new UserBack());
        LoginUserInfoBean userInfo = getUserInfo(this);
        sessionId = userInfo.getSessionId();
        userId = userInfo.getUserId();
        if (userInfo!=null){
            userIntegral.request(userId, sessionId);
        }


        Intent intent = getIntent();
        integralCost = intent.getIntExtra("integralCost", 0);
        id = intent.getIntExtra("id", 0);
        int praise = intent.getIntExtra("praise", 0);
        String thumbnail = intent.getStringExtra("thumbnail");
        String title1 = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String summary = intent.getStringExtra("summary");
        long releaseTime = intent.getLongExtra("releaseTime", 0);
        need.setText(integralCost +"");
        simple.setImageURI(thumbnail);
        title.setText(title1);
       content.setText(summary);
       writer.setText(source);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = formatter.format(releaseTime);
        data.setText(format);
        likenum.setText(praise+"");
        mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.exchange)
    public void ExChange(){
       if (amount>=integralCost){
           newsExChangePresenter.request(userId,sessionId,id,amount);
       }else{
           View inflate = View.inflate(this, R.layout.exchange_seccess, null);
           final Dialog dialog = new Dialog(this);
           dialog.setContentView(inflate);
           dialog.show();
           ImageView back=inflate.findViewById(R.id.back);
           Button delete=inflate.findViewById(R.id.delete);
           Button go=inflate.findViewById(R.id.go);
           back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog.dismiss();
               }
           });
           delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog.dismiss();
               }
           });
           go.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                startActivity(new Intent(IntergralExchange.this,TaskActivity.class));
                finish();
               }
           });
       }
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

    private class UserBack implements ICoreInfe<Result<UserIntegralBean>> {
        @Override
        public void success(Result<UserIntegralBean> data) {
            if (data.getStatus().equals("0000")){
                amount = data.getResult().getAmount();
                mean.setText(amount +"");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Change implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
           if (data.getStatus().equals("0000")){
               Toast.makeText(IntergralExchange.this,data.getMessage(),Toast.LENGTH_LONG).show();
               View inflate = View.inflate(IntergralExchange.this, R.layout.exchange_fill, null);
           final Dialog dialog = new Dialog(IntergralExchange.this);
           dialog.setContentView(inflate);
           dialog.show();

           ImageView back=inflate.findViewById(R.id.back);
               Button ok=inflate.findViewById(R.id.ok_btn);
           back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog.dismiss();
               }
           });
           ok.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog.dismiss();
                   finish();
               }
           });
           }else{
               View inflate = View.inflate(IntergralExchange.this, R.layout.exchange_seccess, null);
               Dialog dialog = new Dialog(IntergralExchange.this);
               dialog.setContentView(inflate);
               dialog.show();
           }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
