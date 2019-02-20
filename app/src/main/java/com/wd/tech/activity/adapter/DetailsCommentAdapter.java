package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.view.NewsDetails;
import com.wd.tech.bean.DetailsCommentsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/2/20.
 * 邮箱：
 * 说明：
 */
public class DetailsCommentAdapter  extends RecyclerView.Adapter<DetailsCommentAdapter.ViewHolder> {
    private final NewsDetails activity;
    private List<DetailsCommentsBean> list;

    public DetailsCommentAdapter(NewsDetails newsDetails) {
        this.activity=newsDetails;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(activity, R.layout.details_comments_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.headimage.setImageURI(list.get(i).getHeadPic());
            viewHolder.name.setText(list.get(i).getNickName());
            viewHolder.content.setText(list.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setList(List<DetailsCommentsBean> result) {
        if (result!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView headimage;
        TextView name,time,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headimage=itemView.findViewById(R.id.headimage);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            content=itemView.findViewById(R.id.content);
        }
    }
}
