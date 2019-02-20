package com.wd.tech.fragment.addfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.wu)
    TextView wu;
    Unbinder unbinder;
    @BindView(R.id.back)
    ImageView back;
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
    }


    @OnClick({R.id.search_image, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_image:
                s = fragment1SearchEdit.getText().toString();

                presenter.request(userId, sessionId, s);
                break;
            case R.id.back:
                break;
        }

    }



    private class ADDF implements ICoreInfe<Result<ByIdUserInfoBean>> {


        @Override
        public void success(Result<ByIdUserInfoBean> data) {
            if (data.getStatus().equals("0000")) {
                ByIdUserInfoBean result = data.getResult();
                wu.setText("");
                back.setVisibility(View.VISIBLE);
                image.setImageURI(result.getHeadPic());
                name.setText(result.getNickName());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
