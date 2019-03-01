package com.wd.tech.activity.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.view.InfoAdvertisingVo;
import com.wd.tech.activity.view.NewsDetails;
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
    //广告
    public static final int TYPE_TWO = 1;
    //不是广告
    public static final int TYPE_THREE = 2;
    private View view;

    public ZXXRecyAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //int itemViewType = getItemViewType(i);
        if (i==TYPE_TWO){
            view = View.inflate(context, R.layout.advertising_item, null);
            return new AdvertisingViewHolder(view);
        }
        if (i==TYPE_THREE){
            view = View.inflate(context, R.layout.zx_xrecycler_item, null);
            return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
        //int itemViewType = getItemViewType(i);
        if (viewHolder instanceof ListViewHolder){
            int whetherCollection = list.get(i).getWhetherCollection();
            if (whetherCollection==2){
                ((ListViewHolder) viewHolder).like.setImageResource(R.mipmap.collect_n);
            }else{
                ((ListViewHolder) viewHolder).like.setImageResource(R.mipmap.collect_s);
            }
            ((ListViewHolder) viewHolder).simple.setImageURI(list.get(i).getThumbnail());
            ((ListViewHolder) viewHolder).title.setText(list.get(i).getTitle());
            ((ListViewHolder) viewHolder).content.setText(list.get(i).getSummary());
            ((ListViewHolder) viewHolder).writer.setText(list.get(i).getSource());
            ((ListViewHolder) viewHolder).data.setText(list.get(i).getReleaseTime()+"");
            ((ListViewHolder) viewHolder).share.setText(list.get(i).getShare()+"");
            ((ListViewHolder) viewHolder).likenum.setText(list.get(i).getCollection()+"");
            ((ListViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,NewsDetails.class);
                    intent.putExtra("id",list.get(i).getId());
                    context.startActivity(intent);
                }
            });
        }
        if (viewHolder instanceof AdvertisingViewHolder){
            //Log.e("aa",list.get(i).getInfoAdvertisingVo().getTitle());
            ((AdvertisingViewHolder) viewHolder).connect.setText(list.get(i).getInfoAdvertisingVo().getContent());
            ((AdvertisingViewHolder) viewHolder).simole.setImageURI(list.get(i).getInfoAdvertisingVo().getPic());
            ((AdvertisingViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,InfoAdvertisingVo.class);
                    intent.putExtra("id",list.get(i).getInfoAdvertisingVo().getUrl());
                    context.startActivity(intent);
                }
            });

        }
       }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getWhetherAdvertising()==1){
            return TYPE_TWO;
        }else if (list.get(position).getWhetherAdvertising()==2){
            return TYPE_THREE;
        }
        return TYPE_TWO;
    }

    public void setList(List<InfoRecommecndListBean> result) {
        if (result!=null){
            this.list.addAll(result);
        }
    }


    private class ListViewHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView title,content,writer,data,share,likenum;
        ImageView like;
        public ListViewHolder(View view) {
            super(view);
            simple=view.findViewById(R.id.simple);
            title=view.findViewById(R.id.title);
            content=view.findViewById(R.id.content);
            writer=view.findViewById(R.id.writer);
            data=view.findViewById(R.id.data);
            share=view.findViewById(R.id.share);
            likenum=view.findViewById(R.id.likenum);
            like=view.findViewById(R.id.like);
        }
    }

    private class AdvertisingViewHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView simole;
        TextView connect;
        public AdvertisingViewHolder(View view) {
            super(view);
            simole=view.findViewById(R.id.simole);
            connect=view.findViewById(R.id.connect);
        }
    }
}
