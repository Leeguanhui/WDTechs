package com.wd.tech.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

public class WelcomeActivity extends WDActivity {
    int timecount = 2;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (timecount == 0) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
                timecount--;
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }).start();
    }

    @Override
    protected void destoryData() {

    }
}
