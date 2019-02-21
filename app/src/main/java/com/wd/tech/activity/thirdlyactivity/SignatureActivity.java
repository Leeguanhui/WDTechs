package com.wd.tech.activity.thirdlyactivity;

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
    EditText editext_sign;
    @BindView(R.id.text_num)
    TextView text_num;
    private ModiftSignaturePresenter modiftSignaturePresenter;
    private LoginUserInfoBean userInfo;
    private String mysign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initView() {
        userInfo = getUserInfo(this);
        editext_sign.addTextChangedListener(new MyTextWatcher());
        modiftSignaturePresenter = new ModiftSignaturePresenter(new ModiftSignaResult());
        mysign = getSharedPreferences("mysign", MODE_PRIVATE).getString("mysign", "");
        if (!(mysign.equals("") && mysign == null)) {
            editext_sign.setText(mysign);
        }
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
            String content = editext_sign.getText().toString();
            int length = content.length();
            text_num.setText(length + "/40");
        }
    }

    @OnClick(R.id.send_btn)
    public void send_btn() {
        String content = editext_sign.getText().toString();
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
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
