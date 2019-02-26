package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.ByTitleBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zxk
 * on 2019/2/19 19:25
 * QQ:666666
 * Describe:
 */
public class SearchRecycleAdapter extends RecyclerView.Adapter<SearchRecycleAdapter.Vh> {
    List<ByTitleBean> list = new ArrayList<>();

    public void addAll(List<ByTitleBean> list) {
        this.list.addAll(list);
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView title;
        TextView name;
        TextView time;

        public Vh(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bytitle_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        ByTitleBean byTitleBean = list.get(i);
        vh.name.setText(byTitleBean.getSource());
        vh.title.setText(byTitleBean.getTitle());
        Date date = new Date();
        date.setTime(byTitleBean.getReleaseTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        vh.time.setText(format);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
