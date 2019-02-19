package com.wd.tech.activity.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.wd.tech.R;
import com.wd.tech.bean.InfoRecommecndListBean;

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
    private ArrayList<String> mimages;
    private ArrayList<String> mtitle;
    private List<InfoRecommecndListBean> list;
    //banner
    public static final int TYPE_ONE = 0;
    //广告
    public static final int TYPE_TWO = 1;
    //不是广告
    public static final int TYPE_THREE = 2;
    private View view;

    public ZXXRecyAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
        this.mimages=new ArrayList<>();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==TYPE_ONE){
            view = View.inflate(context, R.layout.zx_xrecyclerbanner, null);
            return new BannerViewHolder(view);
        }
        if (i==TYPE_TWO){

        }
        if (i==TYPE_THREE){
            view = View.inflate(context, R.layout.zx_xrecycler_item, null);
            return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case TYPE_ONE:
                BannerViewHolder viewHolder1 = (BannerViewHolder) viewHolder;
                viewHolder1.xBanner.setBannerData(R.layout.banner_item,null);
                viewHolder1.xBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        SimpleDraweeView simple = view.findViewById(R.id.simple);
                        TextView title=view.findViewById(R.id.title);
                        simple.setImageURI(mimages.get(position));
                        title.setText(mtitle.get(position));
                    }
                });
                break;
            case TYPE_THREE:
                ListViewHolder viewHolder2 = (ListViewHolder) viewHolder;
                viewHolder2.simple.setImageURI(list.get(i).getThumbnail());
                viewHolder2.title.setText(list.get(i).getTitle());
                viewHolder2.content.setText(list.get(i).getSummary());
                viewHolder2.writer.setText(list.get(i).getSource());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_ONE;
        }else if (list.get(position).getWhetherAdvertising()==1){
            return TYPE_TWO;
        }else if (list.get(position).getWhetherAdvertising()==2){
            return TYPE_THREE;
        }
        return super.getItemViewType(position);
    }

    public void setImages(ArrayList<String> mImages) {
        this.mimages=mImages;
    }

    public void setTiles(ArrayList<String> mItitles) {
        this.mtitle=mItitles;
    }

    public void setList(List<InfoRecommecndListBean> result) {
        this.list=result;
    }

    private class BannerViewHolder extends XRecyclerView.ViewHolder {
        XBanner xBanner;
        public BannerViewHolder(View view) {
            super(view);
            xBanner=view.findViewById(R.id.xbanner);
        }
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
}
