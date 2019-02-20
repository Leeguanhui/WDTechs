package com.wd.tech.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/2/19.
 * 邮箱：
 * 说明：
 */
public class ZXXRecyAdapter extends XRecyclerView.Adapter{
    private final FragmentActivity context;

    private List<InfoRecommecndListBean> list;
    //banner
    public static final int TYPE_ONE = 0;
    //广告
    public static final int TYPE_TWO = 1;
    //不是广告
    public static final int TYPE_THREE = 2;
    private View view;
    private List<String> images;
    private List<String> titles;
    private View mHeadView;

    public ZXXRecyAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType==TYPE_ONE){
            return new BannerViewHolder(mHeadView);
        }
        if (itemViewType==TYPE_TWO){

        }
        if (itemViewType==TYPE_THREE){
            view = View.inflate(context, R.layout.zx_xrecycler_item, null);
            return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, int i) {
        //int itemViewType = getItemViewType(i);
        if (viewHolder instanceof ListViewHolder){

            ((ListViewHolder) viewHolder).simple.setImageURI(list.get(i).getThumbnail());
            ((ListViewHolder) viewHolder).title.setText(list.get(i).getTitle());
            ((ListViewHolder) viewHolder).content.setText(list.get(i).getSummary());
            ((ListViewHolder) viewHolder).writer.setText(list.get(i).getSource());
        }
        /*switch (itemViewType){
            case TYPE_ONE:

                *//*BannerViewHolder viewHolder1 = (BannerViewHolder) viewHolder;
                viewHolder1.banner.setIndicatorVisible(false);
                viewHolder1.banner.setPages(images, new MZHolderCreator<BViewHolder>() {
                    @Override
                    public BViewHolder createViewHolder() {
                        return new BViewHolder();
                    }
                });*//*
                break;
            case TYPE_TWO:

                break;
            case TYPE_THREE:
                ListViewHolder viewHolder2 = (ListViewHolder) viewHolder;
                viewHolder2.simple.setImageURI(list.get(i).getThumbnail());
                viewHolder2.title.setText(list.get(i).getTitle());
                viewHolder2.content.setText(list.get(i).getSummary());
                viewHolder2.writer.setText(list.get(i).getSource());
                break;
        }*/
    }

    public void addHeaderView(View inflate) {
        this.mHeadView=inflate;
        notifyDataSetChanged();
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
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
       /*if (position==0){
           return TYPE_ONE;
       }else{*/
        if (list.get(position).getWhetherAdvertising()==1){
            return TYPE_TWO;
        }else if (list.get(position).getWhetherAdvertising()==2){
            return TYPE_THREE;
        }
        //}

        return TYPE_ONE;
    }

    public void setList(List<InfoRecommecndListBean> result) {
        this.list=result;
    }

    public void setImages(List<String> mImages) {
        this.images=mImages;
    }

    public void setTiles(List<String> mItitles) {
        this.titles=mItitles;
    }


    private class ListViewHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView title,content,writer;
        public ListViewHolder(View view) {
            super(view);
            simple=view.findViewById(R.id.simple);
            title=view.findViewById(R.id.title);
            content=view.findViewById(R.id.content);
            writer=view.findViewById(R.id.writer);
        }
    }

    private class BannerViewHolder extends XRecyclerView.ViewHolder {
        MZBannerView banner;
        public BannerViewHolder(View view) {
            super(view);
            banner=view.findViewById(R.id.banner);
        }
    }
}
