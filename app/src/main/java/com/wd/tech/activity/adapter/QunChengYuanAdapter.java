package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.GroupMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxk
 * on 2019/3/7 10:48
 * QQ:666666
 * Describe:
 */
public class QunChengYuanAdapter extends RecyclerView.Adapter<QunChengYuanAdapter.VH> {
    private Context context;
    private List<GroupMember> list=new ArrayList<>();
    private  ClickListener clickListener;
    public QunChengYuanAdapter(Context context) {
        this.context = context;
    }
    public void addItem(List<GroupMember> members){
        if (members!=null){
            list.addAll(members);
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.qunchengyaun_layout, viewGroup, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        vh.user_iamge.setImageURI(list.get(i).getHeadPic());
        vh.user_name.setText(list.get(i).getNickName());
//        vh.user_content.setText(list.get(i).get);
        vh.user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(list.get(i).getUserId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView user_iamge;
        private final TextView user_name;
        private final TextView user_content;
        private final Button user_btn;

        public VH(@NonNull View itemView) {
            super(itemView);
            user_iamge = itemView.findViewById(R.id.user_iamge);
            user_name = itemView.findViewById(R.id.user_name);
            user_content = itemView.findViewById(R.id.user_content);
            user_btn = itemView.findViewById(R.id.user_btn);
        }
    }
    public interface ClickListener {
        void click(int id);
    }

    public void setOnItemClickListener( ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
