package com.wd.tech.fragment.addfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FindGroupInfo;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.FindGroupInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zxk
 * on 2019/2/19 19:29
 * QQ:666666
 * Describe:
 */
public class Fragment2 extends WDFragment {

    @BindView(R.id.fragment2_search_edit)
    EditText fragment2SearchEdit;
    @BindView(R.id.search2_image)
    ImageView search2Image;
    @BindView(R.id.aaaaa)
    RelativeLayout aaaaa;
    @BindView(R.id.ccc)
    RelativeLayout ccc;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.you_text)
    TextView youText;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.bbb)
    RelativeLayout bbb;
    private FindGroupInfoPresenter presenter;
    private String s;
    private String sessionId;
    private int userId;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment2;
    }

    @Override
    protected void initView() {

        presenter = new FindGroupInfoPresenter(new FindGroup());
        LoginUserInfoBean bean = getUserInfo(getContext());
        if (bean != null) {
            sessionId = bean.getSessionId();
            userId = bean.getUserId();
        }
    }

    @OnClick({R.id.search2_image, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search2_image:
                String s = fragment2SearchEdit.getText().toString();
                Integer integer = Integer.valueOf(s);
                presenter.request(userId,sessionId,integer);
                break;
            case R.id.next:
                break;
        }
    }


    private class FindGroup implements ICoreInfe<Result<FindGroupInfo>> {
        @Override
        public void success(Result<FindGroupInfo> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                FindGroupInfo result = data.getResult();
                image.setImageURI(result.getGroupImage());
                youText.setText(result.getGroupName());
                bbb.setVisibility(View.VISIBLE);
                ccc.setVisibility(View.GONE);
            }
            if (data.getStatus().equals("1001")){
                ccc.setVisibility(View.VISIBLE);
                bbb.setVisibility(View.GONE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
