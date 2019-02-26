package com.wd.tech.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.LoginUserInfoBeanDao;
import com.wd.tech.presenter.AddCommentsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;


/**
 * 作者：夏洪武
 * 时间：2019/1/29.
 * 邮箱：
 * 说明：
 */
public class ZxComments extends Dialog implements CustomAdapt {
    private EditText et;
    private TextView send;
    //private MovieCommentPresenter film_comment_presenter;
    private String name;
    private AddCommentsPresenter addCommentsPresenter;
    private List<LoginUserInfoBean> list;

    public ZxComments(Context context) {
        super(context);
//        super(context, R.style.CustomDialog);
        init(context);
       if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public Context mContext;
    public View mRootView;

    public void init(final Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        send=mRootView.findViewById(R.id.send);
        et=mRootView.findViewById(R.id.et);
        addCommentsPresenter = new AddCommentsPresenter(new Add());
        DaoSession daoSession = DaoMaster.newDevSession(context, LoginUserInfoBeanDao.TABLENAME);
        LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
        list = loginUserInfoBeanDao.queryBuilder().where(LoginUserInfoBeanDao.Properties.Statu.eq("1"))
                .build().list();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sessionId = list.get(0).getSessionId();
                int userId = list.get(0).getUserId();
                String s1 = et.getText().toString().trim();
                if (list.size()==0){
                    Toast.makeText(context,"请先登录",Toast.LENGTH_LONG).show();
                }else{
                    if(s1.isEmpty()){
                        Toast.makeText(context,"格式不正确，请重新填写",Toast.LENGTH_LONG).show();
                    }else{
                        addCommentsPresenter.request(userId,sessionId,s1,name);
                    }
                }

            }
        });
        //film_comment_presenter = new MovieCommentPresenter(new MovieInpuDialog.CallBack());
        /*send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession daoSession = DaoMaster.newDevSession(context, UserBeanDao.TABLENAME);
                UserBeanDao userBeanDao = daoSession.getUserBeanDao();
                List<UserBean> list = userBeanDao.queryBuilder()
                        .where(UserBeanDao.Properties.Register.eq(1))
                        .build()
                        .list();
                String sessionId = list.get(0).getSessionId();
                int userId = list.get(0).getUserId();
                String s1 = et.getText().toString().trim();
                if (name.equals("1")){
                    if(s1.isEmpty()){
                        Toast.makeText(context,"格式不正确，请重新填写",Toast.LENGTH_LONG).show();

                    }else{
                        film_comment_presenter.request(userId,sessionId,s,s1);
                    }
                }else{
                    if(s1.isEmpty()){
                        Toast.makeText(context,"格式不正确，请重新填写",Toast.LENGTH_LONG).show();
                    }else{
                        film_comment_presenter.request(userId,sessionId,s,"@ "+name+" 回复： "+s1);
                    }
                }


            }
        });*/

        setContentView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        if (EventBus.getDefault().isRegistered(this)){
           EventBus.getDefault().unregister(this);
       }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void FilmNum(String s) {
        this.name=s;
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }


    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class Add implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
               if (data.getStatus().equals("0000")){
                   Toast.makeText(getContext(),data.getMessage(),Toast.LENGTH_LONG).show();
                   dismiss();
               }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /*private class CallBack implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getContext(),data.getMessage(),Toast.LENGTH_LONG).show();
                dismiss();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }*/
}

