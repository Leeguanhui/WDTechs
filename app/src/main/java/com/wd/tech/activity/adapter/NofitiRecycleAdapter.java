package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class NofitiRecycleAdapter extends RecyclerView.Adapter<NofitiRecycleAdapter.Vh> {
    List<String> list = new ArrayList<>();

    public void addAll(List<String> list) {
        this.list.addAll(list);
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView nofiti_time;
        TextView nofiti_content;

        public Vh(@NonNull View itemView) {
            super(itemView);
            nofiti_time = itemView.findViewById(R.id.nofiti_time);
            nofiti_content = itemView.findViewById(R.id.nofiti_content);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nofiti_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
