package com.wd.tech.fragment.addfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.UIUtils;
import com.wd.tech.presenter.FindUserByPhonePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zxk
 * on 2019/2/19 19:27
 * QQ:666666
 * Describe:
 */
public class Fragment1 extends WDFragment {
    @BindView(R.id.fragment1_search_edit)
    EditText fragment1SearchEdit;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.you_text)
    TextView name;

    @BindView(R.id.aaaaa)
    RelativeLayout aaaaa;
    @BindView(R.id.ccc)
    RelativeLayout ccc;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.bbb)
    RelativeLayout bbb;
    Unbinder unbinder;
    private FindUserByPhonePresenter presenter;
    private String sessionId;
    private int userId;

    private String s;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment1;
    }

    @Override
    protected void initView() {
        LoginUserInfoBean bean = getUserInfo(getContext());
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }

        presenter = new FindUserByPhonePresenter(new ADDF());
        fragment1SearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    String content = fragment1SearchEdit.getText().toString();
                    fragment1SearchEdit.setText(content);
                    if (TextUtils.isEmpty(content)) {
                        UIUtils.showToastSafe("不能为空");
                    } else {
                        presenter.request(userId, sessionId, s);
                    }
                }
                return false;
            }
        });

    }


    @OnClick({R.id.search_image, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_image:
                s = fragment1SearchEdit.getText().toString();

                presenter.request(userId, sessionId, s);
                break;
            case R.id.next:

                break;
        }

    }

    private class ADDF implements ICoreInfe<Result<ByIdUserInfoBean>> {


        @Override
        public void success(Result<ByIdUserInfoBean> data) {
            if (data.getStatus().equals("0000")) {
                ByIdUserInfoBean result = data.getResult();
                image.setImageURI(result.getHeadPic());
                name.setText(result.getNickName());
                bbb.setVisibility(View.VISIBLE);
                ccc.setVisibility(View.GONE);
            }
            if (data.getStatus().equals("1001")) {
                ccc.setVisibility(View.VISIBLE);
                bbb.setVisibility(View.GONE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
