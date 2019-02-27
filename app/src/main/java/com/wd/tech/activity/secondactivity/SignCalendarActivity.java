package com.wd.tech.activity.secondactivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.activity.view.SignCalendar;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.FindUserSignPresenter;
import com.wd.tech.presenter.FindUserSignRecordingPresenter;
import com.wd.tech.presenter.UserSignPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignCalendarActivity extends WDActivity {
    private SignCalendar calendar;
    private String date;
    private TextView tv_sign_year_month;
    List<String> list = new ArrayList<String>();
    private int month;
    private int year;
    private RelativeLayout rlGetGiftData;
    private TextView tvGetSunValue;
    private ImageView ivSun;
    private ImageView ivSunBg;
    private RelativeLayout rlQuedingBtn;
    private Button rlBtnSign;
    private ImageView signBack;
    private UserSignPresenter userSignPresenter;
    private LoginUserInfoBean userInfo;
    private FindUserSignPresenter findUserSignPresenter;
    private Double resultInt;
    private FindUserSignRecordingPresenter findUserSignRecordingPresenter;
    private DoTheTaskPresenter doTheTaskPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_calendar;
    }

    @Override
    protected void initView() {
        findUserSignRecordingPresenter = new FindUserSignRecordingPresenter(new FinduserRecordResult());
        userInfo = getUserInfo(this);
        //接收传递过来的初始化数据
//        SignCalendarReq signCalendarReq = (SignCalendarReq) getIntent().getSerializableExtra("userInfos");
        //做任务
        doTheTaskPresenter = new DoTheTaskPresenter(new TheTaskResult());
        //用户签到
        userSignPresenter = new UserSignPresenter(new UserSignResult());
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
        //当天签到状态
        findUserSignPresenter = new FindUserSignPresenter(new FindUserResult());
        findUserSignPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);
        calendar = (SignCalendar) findViewById(R.id.sc_main);
        tv_sign_year_month = (TextView) findViewById(R.id.tv_sign_year_month);
        rlGetGiftData = (RelativeLayout) findViewById(R.id.rl_get_gift_view);
        tvGetSunValue = (TextView) findViewById(R.id.tv_text_one);
        ivSun = (ImageView) findViewById(R.id.iv_sun);
        ivSunBg = (ImageView) findViewById(R.id.iv_sun_bg);
        rlQuedingBtn = (RelativeLayout) findViewById(R.id.rl_queding_btn);
        rlBtnSign = (Button) findViewById(R.id.rl_btn_sign);
        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                tv_sign_year_month.setText(year + "年" + (month) + "月");//设置日期
            }
        });
        tv_sign_year_month.setText(year + "年" + (month + 1) + "月");//设置日期
        Log.i("GT", "month：" + month);
//            if (signCalendarReq.getState().getCode() == 1) {
//                dataBean = signCalendarReq.getData();
//                String signDay = dataBean.getSignDay();
//                String[] splitDay = signDay.split(",");
//                for (int i = 0; i < splitDay.length; i++) {
//                    if (Integer.parseInt(splitDay[i]) < 10) {
//                        list.add(year + "-" + (month + 1) + "-0" + splitDay[i]);
//                    } else {
//                        list.add(year + "-" + (month + 1) + "-" + splitDay[i]);
//                    }
//                }
//                calendar.addMarks(list, 0);

        rlBtnSign.setBackgroundResource(R.drawable.click_btn_shapert);
        rlBtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
            }
        });

        rlQuedingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlGetGiftData.setVisibility(View.GONE);
                rlBtnSign.setVisibility(View.VISIBLE);
            }
        });
        calendar.setCalendarDayBgColor(date, R.drawable.jintian);
        //查询用户当月所有签到的日期
        findUserSignRecordingPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 签到回调接口
     */
    private class UserSignResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                calendar.setCalendarDayBgColor(date, R.drawable.yiqiandao);
                calendar.textColor();
                rlBtnSign.setText("已签到");
                rlBtnSign.setClickable(false);
                doTheTaskPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), 1001);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 查看当天签到状态
     */
    private class FindUserResult implements ICoreInfe<Result> {
        private Double resultInt;

        @Override
        public void success(Result result) {
            resultInt = (Double) result.getResult();
            if (resultInt == 1) {
                calendar.setCalendarDayBgColor(date, R.drawable.yiqiandao);
                calendar.textColor();
                rlBtnSign.setText("已签到");
                rlBtnSign.setClickable(false);
            } else {
                calendar.textColor();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 当月签到日
     */
    private class FinduserRecordResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            List<String> SignCalendList = (List<String>) result.getResult();
            for (int i = 0; i < SignCalendList.size(); i++) {
                calendar.setCalendarDayBgColor(SignCalendList.get(i), R.drawable.yiqiandao);
            }
            calendar.textColor();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 做任务
     */
    private class TheTaskResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
