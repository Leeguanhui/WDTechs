package com.wd.tech.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.wd.tech.activity.adapter.ZXXRecyAdapter;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDFragment;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.NewsBannderPresenter;
import com.wd.tech.presenter.ZxInformationPresenter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

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
    private List<String> mImages;
    private List<String> mItitles;
    private ZXXRecyAdapter zxxRecyAdapter;
    private ZxInformationPresenter zxInformationPresenter;
    private int num=10;
    private MZBannerView banner;

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
        initJK();
        View inflate = View.inflate(getActivity(), R.layout.zx_xrecyclerbanner, null);
        banner = inflate.findViewById(R.id.banner);
        initBanner();
        xrecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        xrecy.addHeaderView(inflate);
        xrecy.setAdapter(zxxRecyAdapter);
        zxxRecyAdapter.notifyDataSetChanged();
        xrecy.setPullRefreshEnabled(true);
        xrecy.setLoadingMoreEnabled(true);
        xrecy.refresh();
        xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                newsBannderPresenter.request();
                zxInformationPresenter.request(1, "2",1,1,num+1);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                newsBannderPresenter.request();
                zxInformationPresenter.request(1, "2",1,1,num+1);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.loadMoreComplete();
            }
        });
    }

    private void initBanner() {

        banner.setIndicatorVisible(false);
        banner.setPages(mImages, new MZHolderCreator<BViewHolder>() {
            @Override
            public BViewHolder createViewHolder() {
                return new BViewHolder();
            }
        });
    }
    public static class BViewHolder implements MZViewHolder<String> {
        private SimpleDraweeView mImageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = view.findViewById(R.id.simple);
            return view;
        }

        @Override
        public void onBind(Context context, int i, String string) {
            mImageView.setImageURI(string);
        }

    }
    private void initJK() {
        zxxRecyAdapter = new ZXXRecyAdapter(getActivity());
        newsBannderPresenter = new NewsBannderPresenter(new Bannder());
        zxInformationPresenter = new ZxInformationPresenter(new InforMationList());
        newsBannderPresenter.request();
        zxInformationPresenter.request(1, "2",1,1,num);
    }

    @Override
    public void onResume() {
        super.onResume();
        zxInformationPresenter.request(1, "2",1,1,num);
    }

    private class Bannder implements ICoreInfe<Result<List<NewsBannder>>> {
        @Override
        public void success(Result<List<NewsBannder>> data) {
            List<NewsBannder> result = data.getResult();
            mImages = new ArrayList<>();
            mItitles = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                mImages.add(result.get(i).getImageUrl());
                Log.e("图片", result.get(i).getImageUrl());
                mItitles.add(result.get(i).getTitle());
            }
            zxxRecyAdapter.setImages(mImages);
            zxxRecyAdapter.setTiles(mItitles);
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
            Log.e("aaaa", data.getResult().get(0).getTitle());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
