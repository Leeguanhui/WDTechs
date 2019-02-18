package com.wd.tech.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.PersonallistBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/18 20:12
 * 邮箱：1724959985@qq.com
 */
public class MyListViewAdapter extends BaseAdapter {
    Context context;

    public MyListViewAdapter(Context context) {
        this.context = context;
    }

    List<PersonallistBean> personallistBeans = new ArrayList<>();

    public void addAll(List<PersonallistBean> personallistBeans) {
        this.personallistBeans.addAll(personallistBeans);
    }

    @Override
    public int getCount() {
        return personallistBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return personallistBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHodler myHodler;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.me_list_item, null);
            myHodler = new MyHodler();
            myHodler.image = convertView.findViewById(R.id.image_item);
            myHodler.image2 = convertView.findViewById(R.id.go_next);
            myHodler.textView = convertView.findViewById(R.id.text_item);
            convertView.setTag(myHodler);
        } else {
            myHodler = (MyHodler) convertView.getTag();
        }
        PersonallistBean personallistBean = personallistBeans.get(position);
        myHodler.image.setImageResource(personallistBean.getImage());
        myHodler.textView.setText(personallistBean.getName());
        return convertView;
    }

    class MyHodler {
        ImageView image;
        TextView textView;
        ImageView image2;
    }
}
