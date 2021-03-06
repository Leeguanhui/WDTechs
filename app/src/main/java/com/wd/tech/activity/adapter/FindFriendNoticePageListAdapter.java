package com.wd.tech.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FindFriendNoticePageList;
import com.wd.tech.bean.Result;
import com.wd.tech.core.TimeUtills;

import java.util.ArrayList;
import java.util.List;

public class FindFriendNoticePageListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<FindFriendNoticePageList> list = new ArrayList<>();

    public FindFriendNoticePageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position){
        if (list.get(position).getStatus()==1){
            //未处理
            return 1;
        }else if(list.get(position).getStatus()==2){
            //通过
            return 2;
        }else {
            //拒绝
            return 3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                View inflate1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findfriendnoticepage, viewGroup, false);
                return new Daichuli(inflate1);
            case 2:
                View inflate2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findfriendnoticepagetwo, viewGroup, false);
                return new Agree(inflate2);
            case 3:
                View inflate3 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findfriendnoticepagethree, viewGroup, false);
                return new Jujue(inflate3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof Daichuli) {
            String timedate = TimeUtills.timedate(list.get(i).getNoticeTime());
            ((Daichuli) viewHolder).tvtime.setText(timedate);
            ((Daichuli) viewHolder).sdv.setImageURI(list.get(i).getFromHeadPic());
            ((Daichuli) viewHolder).tvname.setText(list.get(i).getFromNickName());
            ((Daichuli) viewHolder).agree1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(list.get(i).getNoticeId());
                }
            });
            ((Daichuli) viewHolder).jujue1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener1.onItemClick1(list.get(i).getNoticeId());
                }
            });

        }else if (viewHolder instanceof Agree){
            String timedate = TimeUtills.timedate(list.get(i).getNoticeTime());
            ((Agree) viewHolder).tvtime2.setText(timedate);
            ((Agree) viewHolder).sdv2.setImageURI(list.get(i).getFromHeadPic());
            ((Agree) viewHolder).tv_name2.setText(list.get(i).getFromNickName());
        }else{
            String timedate = TimeUtills.timedate(list.get(i).getNoticeTime());
            ((Jujue) viewHolder).tvtime3.setText(timedate);
            ((Jujue) viewHolder).sdv3.setImageURI(list.get(i).getFromHeadPic());
            ((Jujue) viewHolder).tv_name3.setText(list.get(i).getFromNickName());
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addAll(List<FindFriendNoticePageList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }

    //待处理
    class Daichuli extends RecyclerView.ViewHolder {
        TextView tvtime , tvname , agree1 , jujue1;
        SimpleDraweeView sdv;


        public Daichuli(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.longtime1);
            sdv = itemView.findViewById(R.id.sdv_t1);
            tvname = itemView.findViewById(R.id.tv_name1);
            agree1 = itemView.findViewById(R.id.agree1);
            jujue1 = itemView.findViewById(R.id.jujue1);

        }
    }

    //同意
    class Agree extends RecyclerView.ViewHolder {

        TextView tvtime2 , tv_name2;
        SimpleDraweeView sdv2;

        public Agree(@NonNull View itemView) {
            super(itemView);
            tvtime2 = itemView.findViewById(R.id.longtime2);
            tv_name2 = itemView.findViewById(R.id.tv_name2);
            sdv2 = itemView.findViewById(R.id.sdv_t2);

        }
    }

    //拒绝
    class Jujue extends RecyclerView.ViewHolder {

        TextView tvtime3 , tv_name3;
        SimpleDraweeView sdv3;

        public Jujue(@NonNull View itemView) {
            super(itemView);
            tvtime3 = itemView.findViewById(R.id.longtime3);
            tv_name3 = itemView.findViewById(R.id.tv_name3);
            sdv3 = itemView.findViewById(R.id.sdv_t3);


        }
    }
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int noticeId);
    }//定义接口
    public interface OnItemClickListener1 {
        void onItemClick1(int noticeId);
    }

    //方法名
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener1 onItemClickListener1;

    //t方法      设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }//t方法      设置点击方法
    public void setOnItemClickListener1(OnItemClickListener1 onItemClickListener1) {
        this.onItemClickListener1 = onItemClickListener1;
    }
}
