package com.wd.tech.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.IntergralExchange;
import com.wd.tech.activity.adapter.DetailsCommentAdapter;
import com.wd.tech.activity.adapter.DetailsMoreAdapter;
import com.wd.tech.activity.adapter.DetailsTypeAdapter;
import com.wd.tech.bean.AllCommentBean;
import com.wd.tech.bean.DetailsCommentsBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NewsDetailsBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddGreat;
import com.wd.tech.presenter.CancelGreat;
import com.wd.tech.presenter.DetailsCommentsPresenter;
import com.wd.tech.presenter.NewsDetails_Presenter;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.bang)
    ImageView mBang;
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
    private AddGreat addGreat;
    private int id;
    private LoginUserInfoBean userInfo;
    private int whetherGreat;
    private CancelGreat cancelGreat;
    private int comment1;
    private NewsDetailsBean result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }


    @Override
    protected void initView() {
       initP();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userInfo = getUserInfo(this);
        if (userInfo != null) {
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Log.e("qwer", id +"");
        newsDetails_presenter.request(userId, sessionId, id);
        detailsCommentsPresenter.request(userId, sessionId, id, 1, 10);
        comments.setLayoutManager(new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.VERTICAL, false));
        comments.setAdapter(detailsCommentAdapter);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo !=null){
                    EventBus.getDefault().postSticky(String.valueOf(id));
                    ZxComments zxComments = new ZxComments(NewsDetails.this);
                    Window window = zxComments.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    //设置软键盘通常是可见的
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    zxComments.show();
                    zxComments.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            detailsCommentsPresenter.request(userId, sessionId, id, 1, 10);
                        }
                    });
                }else {
                    Toast.makeText(NewsDetails.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initP() {
        detailsTypeAdapter = new DetailsTypeAdapter(this);
        detailsMoreAdapter = new DetailsMoreAdapter(this);
        detailsCommentAdapter = new DetailsCommentAdapter(this);
        detailsCommentsPresenter = new DetailsCommentsPresenter(new Comments());
        newsDetails_presenter = new NewsDetails_Presenter(new DetailsBack());
        addGreat = new AddGreat(new AddGreatBack());
        cancelGreat = new CancelGreat(new CancelBack());
    }

    @OnClick(R.id.great)
    public void Great(){
        if (userInfo != null) {
            if (whetherGreat==1){
                cancelGreat.request(userId,sessionId,id);
            }else{
                addGreat.request(userId,sessionId,id);
            }
        }else{
            Toast.makeText(this,"请先登录",Toast.LENGTH_LONG).show();
        }

   }
    @OnClick(R.id.fufei)
    public void Pay(){
        if (userInfo !=null){
            Dialog dialog = new Dialog(this);
            dialog.setCanceledOnTouchOutside(true);
            final View inflate = View.inflate(this, R.layout.dia_pay_type, null);
            dialog.setContentView(inflate);
            Window dialogWindow = dialog.getWindow();
            WindowManager m = getWindow().getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
            final WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6，根据实际情况调整
            p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
            dialogWindow.setAttributes(p);
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.main_menu_animStyle);

            RelativeLayout mJf=inflate.findViewById(R.id.r);
            RelativeLayout mVip=inflate.findViewById(R.id.t);
            mJf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int integralCost = result.getIntegralCost();
                    int id = result.getId();
                    String thumbnail = result.getThumbnail();
                    String title = result.getTitle();
                    String source = result.getSource();
                    long releaseTime = result.getReleaseTime();
                    int praise = result.getPraise();
                    Intent intent = new Intent(NewsDetails.this, IntergralExchange.class);
                    intent.putExtra("integralCost",integralCost);
                    intent.putExtra("id",id);
                    intent.putExtra("thumbnail",thumbnail);
                    intent.putExtra("title",title);
                    intent.putExtra("source",source);
                    intent.putExtra("releaseTime",releaseTime);
                    intent.putExtra("praise",praise);
                    startActivity(intent);
                }
            });
            mVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            dialog.show();
        }else {
            Toast.makeText(NewsDetails.this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    /*@OnClick(R.id.like)
    public void Like(){
        if (userInfo != null) {
            if (whetherGreat==1){
                cancelGreat.request(userId,sessionId,id);
            }else{
                addGreat.request(userId,sessionId,id);
            }
        }else{
            Toast.makeText(this,"请先登录",Toast.LENGTH_LONG).show();
        }

    }*/
    @Override
    protected void destoryData() {

    }


    private class DetailsBack implements ICoreInfe<Result<NewsDetailsBean>> {

        private int praise;

        @Override
        public void success(Result<NewsDetailsBean> data) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            result = data.getResult();
            int whetherCollection = data.getResult().getWhetherCollection();
            if (whetherCollection==2){
                like.setImageResource(R.mipmap.collect_n);
            }else{
                like.setImageResource(R.mipmap.collect_s);
            }
            whetherGreat = result.getWhetherGreat();
            if (whetherGreat==1){
                mBang.setImageResource(R.drawable.common_icon_p);
            }else{
                mBang.setImageResource(R.drawable.common_icon);
            }
            title.setText(result.getTitle());
            simple.setImageURI(result.getThumbnail());
            comment1 = data.getResult().getComment();
            praise = data.getResult().getPraise();
            commentsnum.setText(data.getResult().getComment()+"");
            likenum.setText(data.getResult().getPraise()+"");
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

    private class Comments implements ICoreInfe<Result<List<AllCommentBean>>> {

        @Override
        public void success(Result<List<AllCommentBean>> data) {
            Log.e("qwerrr",data.getResult().size()+"");
            if (data.getResult().size()==0){
                b.setVisibility(View.GONE);
                return;
            }
            detailsCommentAdapter.setList(data.getResult());
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class AddGreatBack implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
             if (data.getStatus().equals("0000")){
                 Toast.makeText(NewsDetails.this,data.getMessage(),Toast.LENGTH_LONG).show();
                 newsDetails_presenter.request(userId, sessionId, id);
             }else{
                 Toast.makeText(NewsDetails.this,data.getMessage(),Toast.LENGTH_LONG).show();
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CancelBack implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(NewsDetails.this,data.getMessage(),Toast.LENGTH_LONG).show();
                newsDetails_presenter.request(userId, sessionId, id);
            }else{
                Toast.makeText(NewsDetails.this,data.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
