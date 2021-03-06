package com.wd.tech.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.secondactivity.CollectActivity;
import com.wd.tech.bean.FindCollectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class CollectRecycleAdapter extends RecyclerView.Adapter<CollectRecycleAdapter.Vh> {
    List<FindCollectBean> list = new ArrayList<>();
    List<CheckBox> checkBoxes = new ArrayList<>();
    Context context;

    public CollectRecycleAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<FindCollectBean> list) {
        this.list.addAll(list);
    }

    public void deleteAll() {
        list.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {
        SimpleDraweeView qiandao;
        TextView date;
        TextView jifen;
        CheckBox radio;

        public Vh(@NonNull View itemView) {
            super(itemView);
            qiandao = itemView.findViewById(R.id.collect_header);
            date = itemView.findViewById(R.id.collect_name);
            jifen = itemView.findViewById(R.id.collect_content);
            radio = itemView.findViewById(R.id.radio);
            checkBoxes.add(radio);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collect_item, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        final FindCollectBean findCollectBean = list.get(i);
        vh.qiandao.setImageURI(findCollectBean.getThumbnail());
        vh.date.setText(findCollectBean.getTitle());
        vh.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findCollectBean.setClick(isChecked);
                getCheckId();
            }
        });
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.OnClick(findCollectBean.getInfoId());
                }
            }
        });
        //如果商品为选中状态（1）则为true
        if (findCollectBean.isClick()) {
            vh.radio.setChecked(true);
        } else {
            vh.radio.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showRadio() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setVisibility(View.VISIBLE);
        }
    }

    public void hideRadio() {
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setVisibility(View.GONE);
        }
    }

    //批量删除
    public String getCheckId() {
        String mPosition = "";
        for (int i = 0; i < list.size(); i++) {
            FindCollectBean findCollectBean = list.get(i);
            if (findCollectBean.isClick()) {
                int id = findCollectBean.getInfoId();
                mPosition += id + ",";
            }
        }
        return mPosition;
    }

    public onClickListener onClickListener;

    public void setOnClickListener(CollectRecycleAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void OnClick(int id);
    }
}
