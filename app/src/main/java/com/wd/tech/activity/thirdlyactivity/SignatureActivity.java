package com.wd.tech.activity.thirdlyactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.ModiftSignaturePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SignatureActivity extends WDActivity {

    @BindView(R.id.editext_sign)
    EditText mEditextsign;
    @BindView(R.id.text_num)
    TextView mTextnum;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private ModiftSignaturePresenter modiftSignaturePresenter;
    private LoginUserInfoBean userInfo;
    private String mysign;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = getUserInfo(this);
        mEditextsign.addTextChangedListener(new MyTextWatcher());
        modiftSignaturePresenter = new ModiftSignaturePresenter(new ModiftSignaResult());
        mysign = getSharedPreferences("mysign", MODE_PRIVATE).getString("mysign", "");
        if (!(mysign.equals("") && mysign == null)) {
            mEditextsign.setText(mysign);
        }
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }

    @Override
    protected void destoryData() {

    }

    public class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String content = mEditextsign.getText().toString();
            int length = content.length();
            mTextnum.setText(length + "/40");
        }
    }

    @OnClick(R.id.send_btn)
    public void send_btn() {
        content = mEditextsign.getText().toString();
        if (content.equals("") || content == null) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        modiftSignaturePresenter.request(userInfo.getUserId(), userInfo.getSessionId(), content);
    }

    /**
     * 修改个性签名
     */
    private class ModiftSignaResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                edit.putString("mysign", content);
                edit.commit();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
