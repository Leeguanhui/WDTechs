package com.wd.tech.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.UserIntegralListBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class IntegRecycleAdapter extends RecyclerView.Adapter<IntegRecycleAdapter.Vh> {
    List<UserIntegralListBean> list = new ArrayList<>();

    public void addAll(List<UserIntegralListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        list.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView qiandao;
        TextView date;
        TextView jifen;

        public Vh(@NonNull View itemView) {
            super(itemView);
            qiandao = itemView.findViewById(R.id.qiandao);
            date = itemView.findViewById(R.id.date);
            jifen = itemView.findViewById(R.id.jifen);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.integ_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        UserIntegralListBean userIntegralListBean = list.get(i);
        int direction = userIntegralListBean.getDirection();
        if (direction == 1) {
            vh.jifen.setText("+" + userIntegralListBean.getAmount());
        } else {
            vh.jifen.setText("-" + userIntegralListBean.getAmount());
        }
        int type = userIntegralListBean.getType();
        switch (type) {
            case 1:
                vh.qiandao.setText("签到");
                break;
            case 2:
                vh.qiandao.setText("评论");
                break;
            case 3:
                vh.qiandao.setText("分享");
                break;
            case 4:
                vh.qiandao.setText("发帖");
                break;
            case 5:
                vh.qiandao.setText("抽奖收入");
                break;
            case 6:
                vh.qiandao.setText("付费咨询");
                break;
            case 7:
                vh.qiandao.setText("抽奖支出");
                break;
            case 8:
                vh.qiandao.setText("完善个人资信息");
                break;
            case 9:
                vh.qiandao.setText("查看广告");
                break;
            case 10:
                vh.qiandao.setText("绑定第三方");
                break;
        }
        Date date = new Date();
        date.setTime(userIntegralListBean.getCreateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        vh.date.setText(format);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
