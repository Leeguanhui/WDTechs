package com.wd.tech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.utils.Constant;

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
        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
               ||ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  ) {
            // 申请权限
            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
        }
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
