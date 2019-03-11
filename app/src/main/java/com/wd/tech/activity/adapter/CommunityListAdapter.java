package com.wd.tech.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.CommunityUserCommentActivity;
import com.wd.tech.activity.UserPostByIdActivity;
import com.wd.tech.bean.CommunityCommentVoListBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.core.utils.DateUtils;
import com.wd.tech.core.utils.SpacingItemDecoration;
import com.wd.tech.core.utils.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
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

    addCommunityGreat addCommunityGreat;

    public void setAddCommunityGreat(CommunityListAdapter.addCommunityGreat addCommunityGreat) {
        this.addCommunityGreat = addCommunityGreat;
    }

    public interface addCommunityGreat {
        void addCommunityGreat(int id, ImageView addCommunityGreat, String trim, TextView community_praise, CommunityListBean communityListBean);

        void addCommunityComment(int id, ImageView communityIv);

        void onClick(int userId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_communitylist, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        communityListBean = list.get(i);
        //评论列表
        List<CommunityCommentVoListBean> communityUserPostVoList = communityListBean.getCommunityCommentVoList();
        viewHolder.communityUserCommentListAdapter.clean();
        viewHolder.communityUserCommentListAdapter.addItem(communityUserPostVoList);
        viewHolder.communityUserCommentListAdapter.notifyDataSetChanged();

        viewHolder.nickName.setText(communityListBean.getNickName());
        //设置发表时间
        Date date = new Date();
        date.setTime(communityListBean.getPublishTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(format);
            String s = DateUtils.fromToday(d);
            viewHolder.time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.signature.setText(communityListBean.getSignature());
        viewHolder.headPic.setImageURI(communityListBean.getHeadPic());
        if (communityListBean.getContent() != null) {
            viewHolder.content.setVisibility(View.VISIBLE);
            viewHolder.content.setText(communityListBean.getContent());
        } else {
            viewHolder.content.setVisibility(View.GONE);
        }
        viewHolder.comment.setText(String.valueOf(communityListBean.getComment()));
        viewHolder.praise.setText(String.valueOf(communityListBean.getPraise()));

        //判断是否点赞
        if (list.get(i).getWhetherGreat() == 1) {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon_p);
            list.get(i).setCheck(true);
        } else {
            viewHolder.addCommunityGreat.setImageResource(R.drawable.common_icon);
            list.get(i).setCheck(false);
        }

        if (communityUserPostVoList.size() > 0 && communityUserPostVoList.size() <= 3) {
            viewHolder.pl.setText("没有更多评论了");
            viewHolder.pl.setTextColor(android.graphics.Color.parseColor("#666666"));
        }
        if (list.get(i).getComment() > 3) {
            viewHolder.pl.setText("点击查看更多评论");
            viewHolder.pl.setTextColor(android.graphics.Color.parseColor("#87CEFA"));
        }
        if (communityUserPostVoList.size() == 0) {
            viewHolder.pl.setText("快来评论呀");
            viewHolder.pl.setTextColor(android.graphics.Color.parseColor("#666666"));
        }

        viewHolder.pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.pl.getText().toString().trim().equals("点击查看更多评论")) {
                    Intent intent = new Intent(context, CommunityUserCommentActivity.class);
                    intent.putExtra("id", list.get(i).getId());
                    intent.putExtra("headPic", list.get(i).getHeadPic());
                    intent.putExtra("nickName", list.get(i).getNickName());
                    context.startActivity(intent);
                }
            }
        });

        //查看用户动态
        viewHolder.headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommunityGreat.onClick(list.get(i).getUserId());
            }
        });

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
                addCommunityGreat.addCommunityComment(list.get(i).getId(), viewHolder.communityIv);
            }
        });

        if (StringUtils.isEmpty(communityListBean.getFile())) {
            viewHolder.gridView.setVisibility(View.GONE);
        } else {
            viewHolder.gridView.setVisibility(View.VISIBLE);
            String[] images = communityListBean.getFile().split(",");

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

    public void addItem(List<CommunityListBean> circleBeans) {
        if (circleBeans != null) {
            list.addAll(circleBeans);
        }
    }

    public void removeAll() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView headPic;
        TextView nickName;
        TextView time;
        TextView signature;
        RecyclerView gridView;
        GridLayoutManager gridLayoutManager;
        ImageAdapter imageAdapter;
        ImageView communityIv;
        ImageView addCommunityGreat;
        TextView content;
        TextView comment;
        TextView praise;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;
        CommunityUserCommentListAdapter communityUserCommentListAdapter;
        TextView pl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headPic = itemView.findViewById(R.id.community_headpic);
            nickName = itemView.findViewById(R.id.community_nickname);
            time = itemView.findViewById(R.id.community_time);
            signature = itemView.findViewById(R.id.community_signature);
            gridView = itemView.findViewById(R.id.community_recycler);
            content = itemView.findViewById(R.id.community_content);
            communityIv = itemView.findViewById(R.id.community_iv);
            addCommunityGreat = itemView.findViewById(R.id.addcommunitygreat);
            comment = itemView.findViewById(R.id.community_comment);
            praise = itemView.findViewById(R.id.community_praise);
            pl = itemView.findViewById(R.id.pl);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);
            //图片间距
            gridLayoutManager = new GridLayoutManager(context, 3);
            gridView.addItemDecoration(new SpacingItemDecoration(space));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(imageAdapter);
            //评论列表
            recyclerView = itemView.findViewById(R.id.recycler);
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            communityUserCommentListAdapter = new CommunityUserCommentListAdapter(context);
            recyclerView.setAdapter(communityUserCommentListAdapter);
        }
    }


}
