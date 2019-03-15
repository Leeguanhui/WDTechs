package com.wd.tech.activity.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.view.SearchType;
import com.wd.tech.activity.view.ZXType;
import com.wd.tech.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/2/22.
 * 邮箱：
 * 说明：
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    private final ZXType context;
    private List<TypeBean> list;

    public TypeAdapter(ZXType type) {
        this.context=type;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.type_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
          viewHolder.typename.setText(list.get(i).getName());
          viewHolder.image.setImageURI(list.get(i).getPic());
          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(context,SearchType.class);
                  intent.putExtra("id",list.get(i).getId());
                  intent.putExtra("name",list.get(i).getName());
                  context.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setList(List<TypeBean> result) {
        if (this.list!=null){
            this.list.clear();
            this.list=result;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        TextView typename,ename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            typename=itemView.findViewById(R.id.typename);
            ename=itemView.findViewById(R.id.ename);
        }
    }
}
