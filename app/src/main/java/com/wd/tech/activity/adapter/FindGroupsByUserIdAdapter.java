package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FindGroupsByUserId;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxk
 * on 2019/2/25 19:07
 * QQ:666666
 * Describe:
 */
public class FindGroupsByUserIdAdapter extends RecyclerView.Adapter<FindGroupsByUserIdAdapter.VH> {
    private List<FindGroupsByUserId> list=new ArrayList<>();
    private Context context;

    public FindGroupsByUserIdAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<FindGroupsByUserId> byUserIds) {
        if (byUserIds != null) {
            list.clear();
            list.addAll(byUserIds);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.findgroupsbyuserid_layout, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        FindGroupsByUserId groups = list.get(i);
        vh.image.setImageURI(groups.getGroupImage());
        vh.name.setText(groups.getGroupName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;
        private final TextView name;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.qun_iamge);
            name = itemView.findViewById(R.id.qun_name);
        }
    }
}
