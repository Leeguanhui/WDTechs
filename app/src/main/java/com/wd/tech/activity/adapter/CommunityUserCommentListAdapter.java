package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.CommunityUserCommentListBean;

import java.util.ArrayList;
import java.util.List;

public class CommunityUserCommentListAdapter extends RecyclerView.Adapter<CommunityUserCommentListAdapter.ViewHolder> {
    Context context;
    List<CommunityUserCommentListBean> list;

    public CommunityUserCommentListAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_findcommunityusercommentlist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(list.get(i).getNickName());
        viewHolder.textView2.setText(list.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<CommunityUserCommentListBean> lists) {
        if (lists != null) {
            list.addAll(lists);
        }
    }

    public void clean() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.content);
            textView1 = itemView.findViewById(R.id.nickName);
        }
    }
}
