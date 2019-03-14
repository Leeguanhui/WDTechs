package com.wd.tech.activity.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.activity.view.InfoAdvertisingVo;
import com.wd.tech.activity.view.NewsDetails;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.TimeUtils;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.AddCollectionPresenter;
import com.wd.tech.presenter.CancelCollectionPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.wd.tech.core.utils.UIUtils.getResources;

/**
 * 作者：夏洪武
 * 时间：2019/2/19.
 * 邮箱：
 * 说明：
 */
public class ZXXRecyAdapter extends XRecyclerView.Adapter{
    private final FragmentActivity context;
    private LoginUserInfoBean userInfo;

    private List<InfoRecommecndListBean> list;
    //广告
    public static final int TYPE_TWO = 1;
    //不是广告
    public static final int TYPE_THREE = 2;
    private View view;
    private int num=0;
    public ZXXRecyAdapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
       // this.userInfo=userInfo;
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //int itemViewType = getItemViewType(i);
        if (i==TYPE_TWO){
            view = View.inflate(context, R.layout.advertising_item, null);
            return new AdvertisingViewHolder(view);
        }
        if (i==TYPE_THREE){
            view = View.inflate(context, R.layout.zx_xrecycler_item, null);
            return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final XRecyclerView.ViewHolder viewHolder, final int i) {
        //int itemViewType = getItemViewType(i);
        TimeUtils timeUtils = new TimeUtils();
        if (viewHolder instanceof ListViewHolder){
            final int whetherCollection = list.get(i).getWhetherCollection();
            if (whetherCollection==2){
                ((ListViewHolder) viewHolder).like.setImageResource(R.mipmap.collect_n);
            }else{
                ((ListViewHolder) viewHolder).like.setImageResource(R.mipmap.collect_s);
            }

            ((ListViewHolder) viewHolder).ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ClickCollect.ok(i,list.get(i).getId(),whetherCollection,list.get(i).getCollection(),((ListViewHolder) viewHolder).like,((ListViewHolder) viewHolder).likenum);
                }
            });
            ((ListViewHolder) viewHolder).simple.setImageURI(list.get(i).getThumbnail());
            ((ListViewHolder) viewHolder).title.setText(list.get(i).getTitle());
            ((ListViewHolder) viewHolder).content.setText(list.get(i).getSummary());
            ((ListViewHolder) viewHolder).writer.setText(list.get(i).getSource());
            String time = TimeUtils.getDescriptionTimeFromTimestamp(list.get(i).getReleaseTime());
            ((ListViewHolder) viewHolder).data.setText(time);
            ((ListViewHolder) viewHolder).ss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View inflate = View.inflate(context, R.layout.share, null);
                    final Dialog dialog = new Dialog(context);
                    Window dialogWindow = dialog.getWindow();
                    dialogWindow.setGravity(Gravity.CENTER);
                    dialog.setContentView(inflate);
                    dialog.show();
                    RelativeLayout back = inflate.findViewById(R.id.a);
                    RelativeLayout wxq = inflate.findViewById(R.id.wxq);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    wxq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wechatShare(i);
                            dialog.dismiss();
                        }
                    });
                }
            });
            ((ListViewHolder) viewHolder).share.setText(list.get(i).getShare()+"");
            ((ListViewHolder) viewHolder).likenum.setText(list.get(i).getCollection()+"");
            ((ListViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,NewsDetails.class);
                    intent.putExtra("id",list.get(i).getId());
                    context.startActivity(intent);
                }
            });
        }
        if (viewHolder instanceof AdvertisingViewHolder){
            //Log.e("aa",list.get(i).getInfoAdvertisingVo().getTitle());
            ((AdvertisingViewHolder) viewHolder).connect.setText(list.get(i).getInfoAdvertisingVo().getContent());
            ((AdvertisingViewHolder) viewHolder).simole.setImageURI(list.get(i).getInfoAdvertisingVo().getPic());
            ((AdvertisingViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,InfoAdvertisingVo.class);
                    intent.putExtra("id",list.get(i).getInfoAdvertisingVo().getUrl());
                    context.startActivity(intent);
                }
            });

        }
       }
    /**
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     *
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, "wx4c96b6b8da494224", false);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "www.hooxiao.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = list.get(flag).getTitle();
        msg.description = list.get(flag).getSummary();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.wxshare);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getWhetherAdvertising()==1){
            return TYPE_TWO;
        }else if (list.get(position).getWhetherAdvertising()==2){
            return TYPE_THREE;
        }
        return TYPE_TWO;
    }

    public void setList(List<InfoRecommecndListBean> result) {
        if (result!=null){
            this.list.addAll(result);
            notifyDataSetChanged();
        }
    }
    public void GetList(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public void setUser(LoginUserInfoBean userInfo) {
        this.userInfo=userInfo;
    }


    private class ListViewHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView title,content,writer,data,share,likenum;
        ImageView like,sharewith;
        LinearLayout ss,ll;
        public ListViewHolder(View view) {
            super(view);
            simple=view.findViewById(R.id.simple);
            title=view.findViewById(R.id.title);
            content=view.findViewById(R.id.content);
            writer=view.findViewById(R.id.writer);
            data=view.findViewById(R.id.data);
            share=view.findViewById(R.id.share);
            likenum=view.findViewById(R.id.likenum);
            like=view.findViewById(R.id.like);
            sharewith=view.findViewById(R.id.sharewith);
            ss=view.findViewById(R.id.ss);
            ll=view.findViewById(R.id.ll);
        }
    }

    private class AdvertisingViewHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView simole;
        TextView connect;
        public AdvertisingViewHolder(View view) {
            super(view);
            simole=view.findViewById(R.id.simole);
            connect=view.findViewById(R.id.connect);
        }
    }



    public interface Collect{
        void ok(int i,int id,int whetherCollection,int num,ImageView image,TextView textView);
    }
    private Collect ClickCollect;
    public void setOnItemClickLisenter(Collect clickCollect){
        this.ClickCollect=clickCollect;
    }

}
