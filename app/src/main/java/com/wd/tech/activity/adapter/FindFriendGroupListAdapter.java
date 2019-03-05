package com.wd.tech.activity.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FriendGroup;

import java.util.ArrayList;
import java.util.List;

public class FindFriendGroupListAdapter extends RecyclerView.Adapter<FindFriendGroupListAdapter.VH> {
    private List<FriendGroup> list = new ArrayList<>();
    private ClickListener clickListener;
    private ClickListener1 clickListener1;
    private ClickListener2 clickListener2;

    public void setList(List<FriendGroup> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(viewGroup.getContext(), R.layout.find_friend_groups_adapter, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {

        vh.textView.setText(list.get(i).getGroupName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.click(list.get(i).getGroupId());
            }
        });
        vh.delete_imageaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener1.click1(list.get(i).getGroupId());
            }
        });

        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener2.click2(list.get(i).getGroupId());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final ImageView delete_imageaa;
        public VH(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.find_friend_groups_name);
            delete_imageaa=itemView.findViewById(R.id.delete_imageaa);
        }
    }
    public interface ClickListener{
        void click(int id);
    }
    public void setOnItemClickListener(ClickListener clickListener){
      this.clickListener=clickListener;
    }
    public interface ClickListener1{
        void click1(int id);
    }
    public void setOnItemClickListener1(ClickListener1 clickListener1){
        this.clickListener1=clickListener1;
    }public interface ClickListener2{
        void click2(int id);
    }
    public void setOnItemClickListener2(ClickListener2 clickListener2){
        this.clickListener2=clickListener2;
    }
}
