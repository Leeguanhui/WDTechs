package com.wd.tech.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AttUserListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/19 19:33
 * 邮箱：1724959985@qq.com
 */
// TODO: 2019/2/19 穿数据 
public class AttenRecycleAdapter extends BaseAdapter {

    Context context;

    public AttenRecycleAdapter(Context context) {
        this.context = context;
    }

    List<AttUserListBean> attUserListBeans = new ArrayList<>();

    public void addAll(List<AttUserListBean> attUserListBeans) {
        this.attUserListBeans.addAll(attUserListBeans);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        attUserListBeans.clear();
        notifyDataSetChanged();
    }

    public void removePosition(int position) {
        attUserListBeans.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return attUserListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return attUserListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vh vh = null;
        if (convertView == null) {
            vh = new Vh();
            convertView = View.inflate(parent.getContext(), R.layout.atten_item, null);
            vh.header = convertView.findViewById(R.id.atten_header);
            vh.textView = convertView.findViewById(R.id.atten_name);
            vh.textView1 = convertView.findViewById(R.id.atten_content);
            convertView.setTag(vh);
        } else {
            vh = (Vh) convertView.getTag();
        }
        AttUserListBean attUserListBean = attUserListBeans.get(position);
        vh.header.setImageURI(Uri.parse(attUserListBean.getHeadPic()));
        vh.textView.setText(attUserListBean.getNickName());
        vh.textView1.setText(attUserListBean.getSignature());

        return convertView;
    }

    class Vh {
        SimpleDraweeView header;
        TextView textView;
        TextView textView1;
    }

    public List<AttUserListBean> getList() {
        return attUserListBeans;
    }
}
