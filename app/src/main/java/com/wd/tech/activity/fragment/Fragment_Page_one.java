package com.wd.tech.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.ZXXRecyAdapter;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.NewsBannderPresenter;
import com.wd.tech.presenter.ZxInformationPresenter;

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
    @BindView(R.id.xrecy)
    XRecyclerView xrecy;

    private NewsBannderPresenter newsBannderPresenter;
    private ArrayList<String> mImages;
    private ArrayList<String> mItitles;
    private ZXXRecyAdapter zxxRecyAdapter;
    private ZxInformationPresenter zxInformationPresenter;

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
        xrecy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        zxxRecyAdapter = new ZXXRecyAdapter(getActivity());
        newsBannderPresenter = new NewsBannderPresenter(new Bannder());
        zxInformationPresenter = new ZxInformationPresenter(new InforMationList());
        newsBannderPresenter.request();
        zxInformationPresenter.request(1,"2");
        xrecy.setAdapter(zxxRecyAdapter);
    }
    private class Bannder implements ICoreInfe<Result<List<NewsBannder>>> {
        @Override
        public void success(Result<List<NewsBannder>> data) {
            List<NewsBannder> result = data.getResult();
            mImages = new ArrayList<>();
            mItitles = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                mImages.add(result.get(i).getImageUrl());
                mItitles.add(result.get(i).getTitle());
            }
            zxxRecyAdapter.setImages(mImages);
            zxxRecyAdapter.setTiles(mItitles);
            Log.e("ssss",data.getResult().get(0).getTitle());
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class InforMationList implements ICoreInfe<Result<List<InfoRecommecndListBean>>> {
        @Override
        public void success(Result<List<InfoRecommecndListBean>> data) {
            List<InfoRecommecndListBean> result = data.getResult();
            zxxRecyAdapter.setList(result);
            Log.e("aaaa",data.getResult().get(0).getTitle());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
