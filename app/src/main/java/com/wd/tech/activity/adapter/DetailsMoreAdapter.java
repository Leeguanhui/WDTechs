package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.view.NewsDetails;
import com.wd.tech.bean.NewsDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/2/20.
 * 邮箱：
 * 说明：
 */
public class DetailsMoreAdapter extends RecyclerView.Adapter<DetailsMoreAdapter.ViewHolder> {
    private final NewsDetails activity;
    private List<NewsDetailsBean.InformationListBean> list;

    public DetailsMoreAdapter(NewsDetails newsDetails) {
        this.activity=newsDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(activity, R.layout.detailsmore_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
          viewHolder.text.setText(list.get(i).getTitle());
          viewHolder.simple.setImageURI(list.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<NewsDetailsBean.InformationListBean> informationList) {
        this.list=informationList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simple=itemView.findViewById(R.id.simple);
            text=itemView.findViewById(R.id.text);
        }
    }
}
