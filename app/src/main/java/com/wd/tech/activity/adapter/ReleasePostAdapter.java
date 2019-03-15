package com.wd.tech.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.wd.tech.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReleasePostAdapter extends RecyclerView.Adapter<ReleasePostAdapter.ViewHolder> {
    //    private List<Object> mList = new ArrayList<>();
//    private List<Object> mLists = new ArrayList<>();
//    private int sign;//0:普通点击，1自定义
//
//    public void addAll(List<Object> list) {
//        mList.addAll(list);
//    }
//
//    public void setSign(int sign) {
//        this.sign = sign;
//    }
//
//    public interface OnClick {
//        void onClick();
//    }
//
//    OnClick onClick;
//
//    public void setOnClick(OnClick onClick) {
//        this.onClick = onClick;
//    }
//
//    @NonNull
//    @Override
//    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = View.inflate(viewGroup.getContext(), R.layout.releasepost_image_item, null);
//        return new MyHodler(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyHodler myHodler, final int i) {
//        if (mList.get(i) instanceof String) {
//            String imageUrl = (String) mList.get(i);
//            if (imageUrl.contains("http:")) {//加载http
//                myHodler.image.setImageURI(Uri.parse(imageUrl));
//            } else {//加载sd卡
////                String userIdJiequ = imageUrl.substring(imageUrl.indexOf("file://"));
//                Uri uri = Uri.parse("file://" + imageUrl);
//                myHodler.image.setImageURI(uri);
//            }
//        } else {//加载资源文件
//            int id = (int) mList.get(i);
//            Uri uri = Uri.parse("res://com.wd.tech/" + id);
//            myHodler.image.setImageURI(uri);
//        }
//
//        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (sign == 1) {//自定义点击
//                    if (i == 0) {
//                        onClick.onClick();
//                        /*Intent intent = new Intent(
//                                Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        WDActivity.getForegroundActivity().startActivityForResult(intent, WDActivity.PHOTO);*/
//                    } else {
//                        UIUtils.showToastSafe("点击了图片");
//                    }
//                } else {
//                    UIUtils.showToastSafe("点击了图片");
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }
//
//    public void clear() {
//        mList.clear();
//    }
//
//    public void add(Object image) {
//        if (image != null) {
//            mList.add(image);
//        }
//    }
//
//    public List<Object> getList() {
//        return mList;
//    }
//
//    public class MyHodler extends RecyclerView.ViewHolder {
//        SimpleDraweeView image;
//
//        public MyHodler(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.circle_imageview);
//        }
//    }
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 9;
    private Context context;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public ReleasePostAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            ll_del = view.findViewById(R.id.ll_del);
            tv_duration = view.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_filter_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.addpicture);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                    }
                }
            });
            LocalMedia media = list.get(position);
            int mimeType = media.getMimeType();
            String path = "";
            Uri uri;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
                uri = Uri.parse("file://" + path);
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
                uri = Uri.parse("file://" + path);
            } else {
                // 原图
                path = media.getPath();
                uri = Uri.parse("file://" + path);
            }
            // 图片
            if (media.isCompressed()) {
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }

            Log.i("原图地址::", media.getPath());
            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            if (media.isCut()) {
                Log.i("裁剪地址::", media.getCutPath());
            }
            long duration = media.getDuration();
            viewHolder.tv_duration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                viewHolder.tv_duration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
            }
            viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
            } else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(viewHolder.itemView.getContext())
                        .load(uri)
                        .apply(options)
                        .into(viewHolder.mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

        public List<LocalMedia> getList() {
        return list;
    }
}
