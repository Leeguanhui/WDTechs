package com.wd.tech.activity;

import android.widget.EditText;

import com.wd.tech.R;
import com.wd.tech.core.WDActivity;

import butterknife.BindView;

public class RegActivity extends WDActivity {

    @BindView(R.id.mEt_Name_Reg)
    EditText mEt_Name_Reg;
     @BindView(R.id.mEt_Phone_Reg)
    EditText mEt_Phone_Reg;
    @BindView(R.id.mEt_Pwd_Reg)
    EditText mEt_Pwd_Reg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reg;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }
}
