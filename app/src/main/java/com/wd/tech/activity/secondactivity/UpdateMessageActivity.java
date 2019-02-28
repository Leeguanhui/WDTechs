package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.wd.tech.R;
import com.wd.tech.activity.LoginActivity;
import com.wd.tech.activity.ReleasePostActivity;
import com.wd.tech.activity.thirdlyactivity.SignatureActivity;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.activity.view.MyDialog;
import com.wd.tech.activity.view.PickView;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ByIdUserInfoPresenter;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.ModifyNickNamePresenter;
import com.wd.tech.presenter.PerfectUserInfoPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class UpdateMessageActivity extends WDActivity implements CustomAdapt {
    private LoginUserInfoBean userInfo;
    @BindView(R.id.my_name)
    EditText mName;
    @BindView(R.id.my_lin)
    LinearLayout mLin;
    @BindView(R.id.my_sex)
    TextView mSex;
    @BindView(R.id.my_brith)
    TextView mBrith;
    @BindView(R.id.my_mali)
    EditText mMali;

    PickView pickView;
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    MyDialog mDialog;
    private View view;
    private PerfectUserInfoPresenter perfectUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private int sex;
    @BindView(R.id.line2)
    LinearLayout line2;
    private Dialog dialog;
    private DoTheTaskPresenter doTheTaskPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_message;
    }

    @Override
    protected void initView() {
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheReuslt());
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        perfectUserInfoPresenter = new PerfectUserInfoPresenter(new ModifyNameResult());
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        userInfo = getUserInfo(this);
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mName.setText(name);
        view = View.inflate(this, R.layout.sex_itme, null);
        mDialog = new MyDialog(this, view);
        pickView = (PickView) view.findViewById(R.id.pvPickView);
//定义滚动选择器的数据项
        ArrayList<String> grade = new ArrayList<>();
        grade.add("女");
        grade.add("男");
//为滚动选择器设置数据
        pickView.setData(grade);
        pickView.setOnSelectListener(new PickView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Log.i("tag", "选择了" + text);
                mSex.setText(text);
            }
        });
        TextView tv_sexdialog_cancel = view.findViewById(R.id.tv_sexdialog_cancel);
        TextView tv_sexdialog_sure = view.findViewById(R.id.tv_sexdialog_sure);
        tv_sexdialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        tv_sexdialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        int sexnum = 1;
        String name = mName.getText().toString();
        String sex = mSex.getText().toString();
        String brith = mBrith.getText().toString();
        String mail = mMali.getText().toString();
        if (sex.equals("男")) {
            sexnum = 1;
        } else {
            sexnum = 2;
        }
        String mysign = sharedPreferences.getString("mysign", "");
        perfectUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), name, sexnum, mysign, brith, mail);
        dialog = CircularLoading.showLoadDialog(UpdateMessageActivity.this, "加载中...", true);
    }

    @OnClick(R.id.line2)
    public void line2() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                mBrith.setText(formatter.format(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    @OnClick(R.id.line1)
    public void line1() {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    @OnClick(R.id.my_lin)
    public void my_lin() {
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        Window window = mDialog.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        mDialog.show();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


    /**
     * 完善个人资料
     */
    private class ModifyNameResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                doTheTaskPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), 1006);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 根据ID查询信息
     */
    private class ByIdUserResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            ByIdUserInfoBean byIdUserInfoBean = (ByIdUserInfoBean) result.getResult();
            sex = byIdUserInfoBean.getSex();
            if (sex == 1) {
                mSex.setText("男");
            } else {
                mSex.setText("女");
            }
            Date date = new Date();
            date.setTime(byIdUserInfoBean.getBirthday());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            mBrith.setText(format);
            mMali.setText(byIdUserInfoBean.getEmail());
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 做任务接口
     */
    private class DoTheReuslt implements ICoreInfe {
        @Override
        public void success(Object data) {
            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
