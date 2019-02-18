package com.wd.tech.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.NewsBannderPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_one extends WDFragment {

    private NewsBannderPresenter newsBannderPresenter;

    @Override
    public String getPageName() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_one;
    }
    @Override
    protected void initView() {
        newsBannderPresenter = new NewsBannderPresenter(new Bannder());
        newsBannderPresenter.request();
    }

    private class Bannder implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getContext(), "111", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
