package com.wd.tech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.utils.Constant;

import butterknife.BindView;

public class WelcomeActivity extends WDActivity {
    int timecount = 2;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text)
    TextView text;
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
    protected void initView(Bundle savedInstanceState) {
        closeSwipeBack();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ziti.ttf");
        text.setTypeface(typeface);
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);
        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
        } else {
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.REQ_PERM_CAMERA) {
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }
}
