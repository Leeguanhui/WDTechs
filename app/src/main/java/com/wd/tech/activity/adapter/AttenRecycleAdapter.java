package com.wd.tech.activity.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AttUserListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class AttenRecycleAdapter extends RecyclerView.Adapter<AttenRecycleAdapter.Vh> {
    List<AttUserListBean> list = new ArrayList<>();

    public void addAll(List<AttUserListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void deleteAll(){
        list.clear();
        notifyDataSetChanged();
    }
    class Vh extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView textView;
        TextView content;

        public Vh(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.atten_header);
            textView = itemView.findViewById(R.id.atten_name);
            content = itemView.findViewById(R.id.atten_content);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.atten_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        AttUserListBean attUserListBean = list.get(i);
        vh.simpleDraweeView.setImageURI(Uri.parse(attUserListBean.getHeadPic()));
        vh.textView.setText(attUserListBean.getNickName());
        vh.content.setText(attUserListBean.getSignature());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
