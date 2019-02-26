package com.wd.tech.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.UserPostByIdActivity;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.CommunityUserCommentListBean;
import com.wd.tech.bean.CommunityUserVoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.SpacingItemDecoration;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.CommunityUserCommentListPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.ViewHolder> {

    private Context context;
    private List<CommunityListBean> list;
    private CommunityListBean communityListBean;
    private String sessionId;
    private int userId;

    public CommunityListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    addCommunityGreat addCommunityGreat;

    public void setAddCommunityGreat(CommunityListAdapter.addCommunityGreat addCommunityGreat) {
        this.addCommunityGreat = addCommunityGreat;
    }

    public interface addCommunityGreat {
        void addCommunityGreat(int id, ImageView addCommunityGreat, String trim, TextView community_praise, CommunityListBean communityListBean);
    }

    public void add(int userIds, String sessionIds) {
        userId = userIds;
        sessionId = sessionIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_communitylist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.communityUserCommentListPresenter.request(userId, sessionId, list.get(i).getId(), 1, 3);
        communityListBean = list.get(i);
        List<CommunityCommentVoListBean> communityUserPostVoList = communityListBean.getCommunityUserPostVoList();
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

        viewHolder.community_headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserPostByIdActivity.class);
                intent.putExtra("id", list.get(i).getUserId());
                context.startActivity(intent);
            }
        });

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
            viewHolder.imageAdapter.addAll(Arrays.asList(images));
            viewHolder.gridLayoutManager.setSpanCount(colNum);//设置列数
            viewHolder.imageAdapter.notifyDataSetChanged();

            if (list.get(i).getComment() > 0) {
                viewHolder.pl.setText("没有更多评论了");
            }
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
        ImageView addCommunityGreat;
        TextView community_content;
        TextView community_comment;
        TextView community_praise;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;
        CommunityUserCommentListAdapter communityUserCommentListAdapter;
        CommunityUserCommentListPresenter communityUserCommentListPresenter;
        TextView pl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            community_headPic = itemView.findViewById(R.id.community_headPic);
            community_nickName = itemView.findViewById(R.id.community_nickName);
            community_time = itemView.findViewById(R.id.community_time);
            community_signature = itemView.findViewById(R.id.community_signature);
            gridView = itemView.findViewById(R.id.community_recycler);
            community_content = itemView.findViewById(R.id.community_content);
            community_iv1 = itemView.findViewById(R.id.community_iv1);
            addCommunityGreat = itemView.findViewById(R.id.addCommunityGreat);
            community_comment = itemView.findViewById(R.id.community_comment);
            community_praise = itemView.findViewById(R.id.community_praise);
            pl = itemView.findViewById(R.id.pl);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);
            //图片间距
            gridLayoutManager = new GridLayoutManager(context, 3);
            gridView.addItemDecoration(new SpacingItemDecoration(space));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(imageAdapter);

            recyclerView = itemView.findViewById(R.id.recycler);
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            communityUserCommentListAdapter = new CommunityUserCommentListAdapter(context);
            recyclerView.setAdapter(communityUserCommentListAdapter);
            //查看用户评论
            communityUserCommentListPresenter = new CommunityUserCommentListPresenter(new CommunityUserCommentList());
        }

        private class CommunityUserCommentList implements ICoreInfe<Result> {
            @Override
            public void success(Result data) {
                List<CommunityUserCommentListBean> result = (List<CommunityUserCommentListBean>) data.getResult();
                communityUserCommentListAdapter.clean();
                communityUserCommentListAdapter.addItem(result);
                communityUserCommentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void fail(ApiException e) {

            }
        }
    }


}
