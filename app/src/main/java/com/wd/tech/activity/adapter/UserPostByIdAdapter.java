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
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.core.utils.SpacingItemDecoration;
import com.wd.tech.core.utils.StringUtils;

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class UserPostByIdAdapter extends RecyclerView.Adapter<UserPostByIdAdapter.ViewHolder> {

    private Context context;
    private List<CommunityCommentVoListBean> list;

    public UserPostByIdAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    private addCommunityGreat addCommunityGreat;

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

        void addCommunityComment(int id);
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
        viewHolder.content.setText(communityCommentVoListBean.getContent());
        viewHolder.comment.setText(String.valueOf(communityCommentVoListBean.getComment()));
        viewHolder.praise.setText(String.valueOf(communityCommentVoListBean.getPraise()));

        //判断是否点赞
        if (list.get(i).getWhetherGreat() == 1) {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon_p);
            list.get(i).setCheck(true);
        } else {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon);
            list.get(i).setCheck(false);
        }

        //点赞
        viewHolder.addCommunityGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setWhetherGreat(1);
                String trim = viewHolder.praise.getText().toString().trim();
                addCommunityGreat.addCommunityGreat(list.get(i).getId(), viewHolder.addCommunityGreat, trim, viewHolder.praise, list.get(i));
            }
        });

        //评论
        viewHolder.communityIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommunityGreat.addCommunityComment(list.get(i).getId());
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
        ImageView communityIv;
        ImageView addCommunityGreat;
        TextView content;
        TextView comment;
        TextView praise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.community_recycler);
            content = itemView.findViewById(R.id.community_content);
            communityIv = itemView.findViewById(R.id.community_iv1);
            addCommunityGreat = itemView.findViewById(R.id.addcommunitygreat);
            comment = itemView.findViewById(R.id.community_comment);
            praise = itemView.findViewById(R.id.community_praise);
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
