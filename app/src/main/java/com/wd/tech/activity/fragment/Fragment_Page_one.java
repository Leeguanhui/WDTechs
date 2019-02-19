package com.wd.tech.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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
import butterknife.Unbinder;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_one extends WDFragment {

    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.search)
    ImageView search;


    private NewsBannderPresenter newsBannderPresenter;
    private ArrayList<String> mImages;
    private ArrayList<String> mItitles;
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
    private class Bannder implements ICoreInfe<Result<List<NewsBannder>>> {
        @Override
        public void success(Result<List<NewsBannder>> data) {
            List<NewsBannder> result = data.getResult();
            mImages = new ArrayList<>();
            mItitles = new ArrayList<>();
            for (int i=0;i<result.size();i++){
                mImages.add(result.get(i).getImageUrl());
                mItitles.add(result.get(i).getTitle());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
