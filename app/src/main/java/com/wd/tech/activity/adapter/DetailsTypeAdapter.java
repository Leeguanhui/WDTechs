package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class DetailsTypeAdapter extends RecyclerView.Adapter<DetailsTypeAdapter.ViewHolder> {
    private final NewsDetails activity;
    private List<NewsDetailsBean.PlateBean> list;

    public DetailsTypeAdapter(NewsDetails newsDetails) {
        this.activity=newsDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(activity, R.layout.detailstype_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text.setText(list.get(i).getName());

    }

    @Override
    public int getItemCount() {
        if (this.list!=null){
            return this.list.size();
        }
        return 0;
    }

    public void setList(List<NewsDetailsBean.PlateBean> plate) {
        if (this.list!=null){
            this.list.clear();
            this.list=plate;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
        }
    }
}
