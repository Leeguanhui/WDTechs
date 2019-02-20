package com.wd.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlockActivity extends WDActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.qun_edittext_name)
    EditText qunEdittextName;
    @BindView(R.id.qun_edittext_content)
    EditText qunEdittextContent;
    @BindView(R.id.text_login)
    TextView textLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flock;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R.id.back, R.id.text_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.text_login:

                break;
        }
    }
}
