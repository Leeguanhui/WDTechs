package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.activity.thirdlyactivity.SearchActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/3/1.
 * 邮箱：
 * 说明：
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final SearchActivity activity;
    private List<String> list;

    public SearchAdapter(SearchActivity searchActivity) {
        this.activity=searchActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(activity, R.layout.search_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       viewHolder.one.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setList(List<String> list) {
        if (list.size()!=0){
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView one;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            one=itemView.findViewById(R.id.one);
        }
    }
}
