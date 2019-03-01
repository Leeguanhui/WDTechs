package com.wd.tech.activity.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.core.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class ReleasePostAdapter extends RecyclerView.Adapter<ReleasePostAdapter.MyHodler> {
    private List<Object> mList = new ArrayList<>();
    private List<Object> mLists = new ArrayList<>();
    private int sign;//0:普通点击，1自定义

    public void addAll(List<Object> list) {
        mList.addAll(list);
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public interface OnClick {
        void onClick();
    }

    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.releasepost_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, final int i) {
        if (mList.get(i) instanceof String) {
            String imageUrl = (String) mList.get(i);
            if (imageUrl.contains("http:")) {//加载http
                myHodler.image.setImageURI(Uri.parse(imageUrl));
            } else {//加载sd卡
//                String userIdJiequ = imageUrl.substring(imageUrl.indexOf("file://"));
                Uri uri = Uri.parse("file://" + imageUrl);
                myHodler.image.setImageURI(uri);
            }
        } else {//加载资源文件
            int id = (int) mList.get(i);
            Uri uri = Uri.parse("res://com.wd.tech/" + id);
            myHodler.image.setImageURI(uri);
        }

        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign == 1) {//自定义点击
                    if (i == 0) {
                        onClick.onClick();
                        /*Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        WDActivity.getForegroundActivity().startActivityForResult(intent, WDActivity.PHOTO);*/
                    } else {
                        UIUtils.showToastSafe("点击了图片");
                    }
                } else {
                    UIUtils.showToastSafe("点击了图片");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }

    public List<Object> getList() {
        return mList;
    }

    public class MyHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circle_imageview);
        }
    }
}
