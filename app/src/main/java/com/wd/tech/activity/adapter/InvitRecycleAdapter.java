package com.wd.tech.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AttUserListBean;
import com.wd.tech.bean.FindMyPostListBean;
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

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class InvitRecycleAdapter extends RecyclerView.Adapter<InvitRecycleAdapter.Vh> {
    List<FindMyPostListBean> list = new ArrayList<>();
    private Context context;

    public InvitRecycleAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<FindMyPostListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_post, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Vh vh, final int i) {
        final FindMyPostListBean findMyPostListBean = list.get(i);
        vh.item_post_textview.setText(findMyPostListBean.getContent());
        Date date = new Date();
        date.setTime(findMyPostListBean.getPublishTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(format);
            String s = DateUtils.fromToday(d);
            vh.item_post_textviewtime.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vh.item_post_textviewnumberone.setText(findMyPostListBean.getPraise() + "");
        vh.item_post_textviewnumbertwo.setText(findMyPostListBean.getComment() + "");
        if (StringUtils.isEmpty(findMyPostListBean.getFile())) {
            vh.item_post_recyclerview.setVisibility(View.GONE);
        } else {
            vh.item_post_recyclerview.setVisibility(View.VISIBLE);
            String[] images = findMyPostListBean.getFile().split(",");
            int imageCount = images.length;
            int colNum;//列数
            if (imageCount == 1) {
                colNum = 1;
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2;
            } else {
                colNum = 3;
            }
            vh.imageAdapter.clear();//清空
            vh.imageAdapter.addAll(Arrays.asList(images));
            vh.gridLayoutManager.setSpanCount(colNum);//设置列数
            vh.imageAdapter.notifyDataSetChanged();
        }
        vh.item_post_textviewdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.getUserId(i,findMyPostListBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeByPosition(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView item_post_textview;
        TextView item_post_textviewtime;
        TextView item_post_textviewnumberone;
        TextView item_post_textviewnumbertwo;
        TextView item_post_textviewdelete;
        RecyclerView item_post_recyclerview;
        ImageAdapter imageAdapter;
        GridLayoutManager gridLayoutManager;

        public Vh(@NonNull View itemView) {
            super(itemView);
            item_post_textview = itemView.findViewById(R.id.item_post_textview);
            item_post_textviewtime = itemView.findViewById(R.id.item_post_textviewtime);
            item_post_textviewnumberone = itemView.findViewById(R.id.item_post_textviewnumberone);
            item_post_textviewnumbertwo = itemView.findViewById(R.id.item_post_textviewnumbertwo);
            item_post_textviewdelete = itemView.findViewById(R.id.item_post_textviewdelete);
            item_post_recyclerview = itemView.findViewById(R.id.item_post_recyclerview);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);
            //图片间距
            gridLayoutManager = new GridLayoutManager(context, 3);
            item_post_recyclerview.addItemDecoration(new SpacingItemDecoration(space));
            item_post_recyclerview.setLayoutManager(gridLayoutManager);
            item_post_recyclerview.setAdapter(imageAdapter);
        }
    }

    public itemClickListener itemClickListener;

    public void setItemClickListener(InvitRecycleAdapter.itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface itemClickListener {
        void getUserId(int position,int userid);
    }
}
