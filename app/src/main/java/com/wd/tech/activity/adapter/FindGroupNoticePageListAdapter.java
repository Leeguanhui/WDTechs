package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FindGroupNoticePageList;
import com.wd.tech.core.TimeUtills;

import java.util.ArrayList;
import java.util.List;


public class FindGroupNoticePageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<FindGroupNoticePageList> list = new ArrayList<>();

    public FindGroupNoticePageListAdapter(Context context) {
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
                View inflate1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findgroupnoticepagelist, viewGroup, false);
                return new Daichuli(inflate1);
            case 2:
                View inflate2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findgroupnoticepagelisttwo, viewGroup, false);
                return new Agree(inflate2);
            case 3:
                View inflate3 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_findgroupnoticepagelistthree, viewGroup, false);
                return new Jujue(inflate3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof Daichuli) {
            String timedate = TimeUtills.timedate(list.get(i).getNoticeTime());
            ((Daichuli) viewHolder).tvtime.setText(timedate);
            ((Daichuli) viewHolder).tvname.setText(list.get(i).getGroupName());
            ((Daichuli) viewHolder).sdv.setImageURI(list.get(i).getHeadPic());
            ((Daichuli) viewHolder).group_tv_request1.setText("邀请你加入"+"“"+list.get(i).getGroupName()+"”");
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
            ((Agree) viewHolder).sdv2.setImageURI(list.get(i).getHeadPic());
            ((Agree) viewHolder).tv_name2.setText(list.get(i).getNickName());
            ((Agree)viewHolder).group_tv_request2.setText("邀请你加入"+"“"+list.get(i).getGroupName()+"”");
        }else{
            String timedate = TimeUtills.timedate(list.get(i).getNoticeTime());
            ((Jujue) viewHolder).tvtime3.setText(timedate);
            ((Jujue) viewHolder).sdv3.setImageURI(list.get(i).getHeadPic());
            ((Jujue) viewHolder).tv_name3.setText(list.get(i).getGroupName());
            ((Jujue) viewHolder).group_tv_request_jujue.setText("邀请你加入"+"“"+list.get(i).getGroupName()+"”");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(List<FindGroupNoticePageList> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    //待处理
    class Daichuli extends RecyclerView.ViewHolder {
        TextView tvtime , tvname , agree1 , jujue1,group_tv_request1;
        SimpleDraweeView sdv;


        public Daichuli(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.group_longtime);
            sdv = itemView.findViewById(R.id.group_sdv_t1);
            tvname = itemView.findViewById(R.id.group_tv_name1);
            agree1 = itemView.findViewById(R.id.group_agree1);
            jujue1 = itemView.findViewById(R.id.group_jujue1);
            group_tv_request1 = itemView.findViewById(R.id.group_tv_request1);

        }
    }

    //同意
    class Agree extends RecyclerView.ViewHolder {

        TextView tvtime2 , tv_name2;
        SimpleDraweeView sdv2;
        private final TextView group_tv_request2;

        public Agree(@NonNull View itemView) {
            super(itemView);
            tvtime2 = itemView.findViewById(R.id.group_longtime2);
            tv_name2 = itemView.findViewById(R.id.group_tv_name2);
            sdv2 = itemView.findViewById(R.id.group_sdv_t2);
            group_tv_request2 = itemView.findViewById(R.id.group_tv_request2);
        }
    }

    //拒绝
    class Jujue extends RecyclerView.ViewHolder {

        TextView tvtime3 , tv_name3,group_tv_request_jujue;
        SimpleDraweeView sdv3;

        public Jujue(@NonNull View itemView) {
            super(itemView);
            tvtime3 = itemView.findViewById(R.id.group_longtime3);
            tv_name3 = itemView.findViewById(R.id.group_tv_name3);
            sdv3 = itemView.findViewById(R.id.group_sdv_t3);
            group_tv_request_jujue = itemView.findViewById(R.id.group_tv_request_jujue);


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
