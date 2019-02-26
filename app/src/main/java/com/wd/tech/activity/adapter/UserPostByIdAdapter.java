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

import com.wd.tech.R;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.core.utils.SpacingItemDecoration;
import com.wd.tech.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPostByIdAdapter extends RecyclerView.Adapter<UserPostByIdAdapter.ViewHolder> {

    private Context context;
    private List<CommunityCommentVoListBean> list;

    public UserPostByIdAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    addCommunityGreat addCommunityGreat;

    public void setAddCommunityGreat(addCommunityGreat addCommunityGreat) {
        this.addCommunityGreat = addCommunityGreat;
    }

    public void addItem(List<CommunityCommentVoListBean> circleBeans) {
        if (circleBeans != null) {
            list.addAll(circleBeans);
        }
    }

    public interface addCommunityGreat {
        void addCommunityGreat(int id, ImageView addCommunityGreat, String trim, TextView community_praise, CommunityCommentVoListBean communityListBean);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_userpostbyid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        CommunityCommentVoListBean communityCommentVoListBean = list.get(i);
        viewHolder.community_content.setText(communityCommentVoListBean.getContent());
        viewHolder.community_comment.setText(String.valueOf(communityCommentVoListBean.getComment()));
        viewHolder.community_praise.setText(String.valueOf(communityCommentVoListBean.getPraise()));

        //判断是否点赞
        if (list.get(i).getWhetherGreat() == 1) {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon_p);
        } else {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon);
        }

        //点赞
        viewHolder.addCommunityGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setWhetherGreat(1);
                String trim = viewHolder.community_praise.getText().toString().trim();
                addCommunityGreat.addCommunityGreat(list.get(i).getId(), viewHolder.addCommunityGreat, trim, viewHolder.community_praise, list.get(i));
            }
        });

        if (StringUtils.isEmpty(communityCommentVoListBean.getFile())) {
            viewHolder.gridView.setVisibility(View.GONE);
        } else {
            viewHolder.gridView.setVisibility(View.VISIBLE);
            String[] images = communityCommentVoListBean.getFile().split(",");

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView gridView;
        GridLayoutManager gridLayoutManager;
        ImageAdapter imageAdapter;
        ImageView community_iv1;
        ImageView addCommunityGreat;
        TextView community_content;
        TextView community_comment;
        TextView community_praise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.community_recycler);
            community_content = itemView.findViewById(R.id.community_content);
            community_iv1 = itemView.findViewById(R.id.community_iv1);
            addCommunityGreat = itemView.findViewById(R.id.addCommunityGreat);
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
