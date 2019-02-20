package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.core.utils.SpacingItemDecoration;
import com.wd.tech.core.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.ViewHolder> {

    private Context context;
    private List<CommunityListBean> list;
    private CommunityListBean communityListBean;

    public CommunityListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_communitylist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        communityListBean = list.get(i);
        viewHolder.community_nickName.setText(communityListBean.getNickName());
        Date date = new Date();
        date.setTime(communityListBean.getPublishTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        viewHolder.community_time.setText(sdf.format(date));
        viewHolder.community_signature.setText(communityListBean.getSignature());
        viewHolder.community_headPic.setImageURI(communityListBean.getHeadPic());
        viewHolder.community_content.setText(communityListBean.getContent());
        viewHolder.community_comment.setText(String.valueOf(communityListBean.getComment()));
        viewHolder.community_praise.setText(String.valueOf(communityListBean.getPraise()));


        if (StringUtils.isEmpty(communityListBean.getFile())) {
            viewHolder.gridView.setVisibility(View.GONE);
        } else {
            viewHolder.gridView.setVisibility(View.VISIBLE);
            String[] images = communityListBean.getFile().split(",");

//            int imageCount = (int) (Math.random() * 9) + 1;
            int imageCount = images.length;

            int colNum;//列数
            if (imageCount == 1) {
                colNum = 1;
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2;
            } else {
                colNum = 3;
            }
            viewHolder.imageAdapter.clear();//清空
//            for (int j = 0; j < imageCount; j++) {
//                viewHolder.imageAdapter.addAll(Arrays.asList(images));
//            }
            viewHolder.imageAdapter.addAll(Arrays.asList(images));
            viewHolder.gridLayoutManager.setSpanCount(colNum);//设置列数
            viewHolder.imageAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<CommunityListBean> circleBeans) {
        if (circleBeans != null) {
            list.addAll(circleBeans);
        }
    }

    public void removeAll() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView community_headPic;
        TextView community_nickName;
        TextView community_time;
        TextView community_signature;
        RecyclerView gridView;
        GridLayoutManager gridLayoutManager;
        ImageAdapter imageAdapter;
        ImageView community_iv1;
        ImageView community_iv2;
        TextView community_content;
        TextView community_comment;
        TextView community_praise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            community_headPic = itemView.findViewById(R.id.community_headPic);
            community_nickName = itemView.findViewById(R.id.community_nickName);
            community_time = itemView.findViewById(R.id.community_time);
            community_signature = itemView.findViewById(R.id.community_signature);
            gridView = itemView.findViewById(R.id.community_recycler);
            community_content = itemView.findViewById(R.id.community_content);
            community_iv1 = itemView.findViewById(R.id.community_iv1);
            community_iv2 = itemView.findViewById(R.id.community_iv2);
            community_comment = itemView.findViewById(R.id.community_comment);
            community_praise = itemView.findViewById(R.id.community_praise);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);
            //图片间距
            gridLayoutManager = new GridLayoutManager(context, 3);
            gridView.addItemDecoration(new SpacingItemDecoration(space));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(imageAdapter);
        }
    }
}
