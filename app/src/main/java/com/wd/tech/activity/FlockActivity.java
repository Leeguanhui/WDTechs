package com.wd.tech.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.CreateGroupPresenter;

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
    private String namE;
    private String contenT;
    private CreateGroupPresenter groupPresenter;
    private LoginUserInfoBean infoBean;
    private String sessionId;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flock;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        groupPresenter = new CreateGroupPresenter(new Creat());
        infoBean = getUserInfo(this);
        if (infoBean != null) {
            sessionId = infoBean.getSessionId();
            userId = infoBean.getUserId();
        }
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
                namE = qunEdittextName.getText().toString();
                contenT = qunEdittextContent.getText().toString();
                if (TextUtils.isEmpty(contenT)) {
                    Toast.makeText(this, "群名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtils.isEmpty(contenT)) {
//                    Toast.makeText(this, "介绍不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (!checkNameChese(namE)) {
                    Toast.makeText(this, "群名必须为中文", Toast.LENGTH_SHORT).show();
                    return;
                }
                groupPresenter.request(userId, sessionId, namE, contenT);
                break;
        }
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    private class Creat implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
