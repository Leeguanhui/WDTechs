package com.wd.tech.activity.adapter;

import android.content.Intent;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
          viewHolder.text.setText(list.get(i).getTitle());
          viewHolder.simple.setImageURI(list.get(i).getThumbnail());
          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(activity,NewsDetails.class);
                  intent.putExtra("id",list.get(i).getId());
                  activity.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        if (this.list!=null){
            return this.list.size();
        }
        return 0;
    }

    public void setList(List<NewsDetailsBean.InformationListBean> informationList) {
        if (informationList!=null){
            this.list.size();
            this.list=informationList;
            notifyDataSetChanged();
        }
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
