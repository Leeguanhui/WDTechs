package com.wd.tech.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.ZXXRecyAdapter;
import com.wd.tech.activity.view.InfoAdvertisingVo;
import com.wd.tech.activity.thirdlyactivity.SearchActivity;
import com.wd.tech.activity.view.NewsDetails;
import com.wd.tech.activity.view.Type;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.LoginUserInfoBean;
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
import butterknife.OnClick;

/**
 * 作者：古祥坤 on 2019/2/18 15:50
 * 邮箱：1724959985@qq.com
 */
public class Fragment_Page_one extends WDFragment {

    @BindView(R.id.menu)
    ImageView menu;

    @BindView(R.id.xrecy)
    XRecyclerView xrecy;
    @BindView(R.id.layout)
    SmartRefreshLayout mLayout;
    private NewsBannderPresenter newsBannderPresenter;
    private List<String> mImages;
    private List<String> mItitles;
    private ZXXRecyAdapter zxxRecyAdapter;
    private ZxInformationPresenter zxInformationPresenter;
    private int NUM=1;
    private MZBannerView banner;
    private SimpleDraweeView mImageView;
    private TextView title;
    private LoginUserInfoBean userInfo;
    private int userId;
    private String sessionId;
    private List<NewsBannder> result;

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
        userInfo = getUserInfo(getContext());
        if (userInfo!=null){
            userId = userInfo.getUserId();
            sessionId = userInfo.getSessionId();
        }
        initJK();
        View inflate = View.inflate(getActivity(), R.layout.zx_xrecyclerbanner, null);
        banner = inflate.findViewById(R.id.banner);
        xrecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        xrecy.setAdapter(zxxRecyAdapter);
        xrecy.addHeaderView(inflate);
        zxxRecyAdapter.notifyDataSetChanged();
        xrecy.setPullRefreshEnabled(true);
        xrecy.setLoadingMoreEnabled(true);
        xrecy.refresh();
        mLayout.setEnableRefresh(true);//启用刷新
        mLayout.setEnableLoadmore(true);//启用加载
        mLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                newsBannderPresenter.request();
                NUM++;
                zxInformationPresenter.request(userId, sessionId,0,NUM,10);
                zxxRecyAdapter.notifyDataSetChanged();
                mLayout.finishRefresh();
            }
        });
        mLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                newsBannderPresenter.request();
                NUM++;
                zxInformationPresenter.request(userId, sessionId,0,NUM,10);
                zxxRecyAdapter.notifyDataSetChanged();
                mLayout.finishLoadmore();
            }
        });
        xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                newsBannderPresenter.request();
                NUM++;
                zxInformationPresenter.request(userId, sessionId,0,NUM,10);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                newsBannderPresenter.request();
                NUM++;
                zxInformationPresenter.request(userId, sessionId,0,NUM,10);
                zxxRecyAdapter.notifyDataSetChanged();
                xrecy.loadMoreComplete();
            }
        });
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                String jumpUrl = result.get(i).getJumpUrl();
                String[] split = jumpUrl.split(":");
                if (split[0].equals("wd")){
                    Intent intent = new Intent(getActivity(),NewsDetails.class);
                    intent.putExtra("id",1);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(),InfoAdvertisingVo.class);
                    intent.putExtra("id",result.get(i).getJumpUrl());
                    startActivity(intent);
                }

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Type.class));
            }
        });
        xrecy.setOverScrollMode(View.OVER_SCROLL_NEVER);

    }
    private void initJK() {
        zxxRecyAdapter = new ZXXRecyAdapter(getActivity());
        newsBannderPresenter = new NewsBannderPresenter(new Bannder());
        zxInformationPresenter = new ZxInformationPresenter(new InforMationList());
        zxInformationPresenter.request(userId, sessionId,0,NUM,10);

    }

    /**
     * 搜索
     */
    @OnClick(R.id.search)
    public void search(){
        startActivity(new Intent(getContext(),SearchActivity.class));
    }
    @Override
    public void onResume() {
        super.onResume();
        zxInformationPresenter.request(userId, sessionId,0,NUM,10);
        banner.start();
    }

    private class Bannder implements ICoreInfe<Result<List<NewsBannder>>> {
        @Override
        public void success(final Result<List<NewsBannder>> data) {
            result = data.getResult();
            mImages = new ArrayList<>();
            mItitles = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                mImages.add(result.get(i).getImageUrl());
                mItitles.add(result.get(i).getTitle());
            }
            banner.setIndicatorVisible(false);
            banner.setPages(mImages,new MZHolderCreator(){
                @Override
                public MZViewHolder createViewHolder() {
                    return new MZViewHolder() {
                        @Override
                        public View createView(Context context) {
                            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
                            mImageView = view.findViewById(R.id.simple);
                            title=view.findViewById(R.id.title);
                            return view;
                        }

                        @Override
                        public void onBind(Context context, int i, Object o) {
                            mImageView.setImageURI(mImages.get(i));
                            title.setText(mItitles.get(i));
                        }
                    };
                }
            });
            banner.start();

        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class InforMationList implements ICoreInfe<Result<List<InfoRecommecndListBean>>> {
        @Override
        public void success(Result<List<InfoRecommecndListBean>> data) {
            newsBannderPresenter.request();
            List<InfoRecommecndListBean> result = data.getResult();
            zxxRecyAdapter.setList(result);
            Log.e("aaaa", data.getResult()+"");
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
