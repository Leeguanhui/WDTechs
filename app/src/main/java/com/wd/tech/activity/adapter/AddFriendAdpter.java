package com.wd.tech.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.LoginUserInfoBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zxk
 * on 2019/2/20 15:04
 * QQ:666666
 * Describe:
 */
public class AddFriendAdpter extends BaseAdapter {
    private List<LoginUserInfoBean> list;
    private Context context;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.activity_add_friends,null);
            SimpleDraweeView image=convertView.findViewById(R.id.image);
            TextView name=convertView.findViewById(R.id.name);
            ImageView next=convertView.findViewById(R.id.next);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
//            holder.image.setImageResource(list.get(position).get);
        }
        return null;
    }
    class ViewHolder{
        SimpleDraweeView image;
        TextView name;
        ImageView next;
    }
}
