package com.wd.tech.core;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wd.tech.R;
import com.wd.tech.activity.IMActivity;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.core.utils.DaoUtils;
import com.wd.tech.face.FaceDB;
import com.wd.tech.greendao.FindConversationListDao;

import java.io.File;
import java.util.List;


/**
 * @name: MyApplication
 * @remark:
 */
public class WDApplication extends Application {
    /**
     * 人脸识别
     */
    private final String TAG = this.getClass().toString();
    public FaceDB mFaceDB;
    Uri mImage;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    private static int MAX_MEM = 30 * ByteConstants.MB;

    /**
     * context 全局唯一的上下文
     */
    private static Context context;

    private static SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        sharedPreferences = getSharedPreferences("share.xml", MODE_PRIVATE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "frescocache");
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this).
                setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this)
                                .setBaseDirectoryPath(file)
                                .build()
                ).build());
        //定位
        //推送
        //统计

        //fresco
        Fresco.initialize(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        EaseUI.getInstance().init(this, null);
        EMClient.getInstance().setDebugMode(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        EaseUI.getInstance().init(this, null);
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this, null);
        EMClient.getInstance().setDebugMode(true);

        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                username = username.toLowerCase();
                EaseUser easeUser = new EaseUser(username);
                List<FindConversationList> aa = DaoUtils.getInstance().getConversationDao().loadAll();
                FindConversationList conversation = DaoUtils.getInstance().getConversationDao().queryBuilder().where(FindConversationListDao.Properties.UserName.eq(username)).build().unique();
                if (conversation != null) {
                    easeUser.setNickname(conversation.getNickName());
                    easeUser.setAvatar(conversation.getHeadPic());
                }

                return easeUser;
            }
        });
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(WDApplication.this);
                        Intent intent = new Intent(WDApplication.this, IMActivity.class);//将要跳转的界面
                        intent.putExtra(EaseConstant.EXTRA_USER_ID, list.get(i).getUserName());
                        intent.putExtra("userNames", list.get(i).getUserName());
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                        //Intent intent = new Intent();//只显示通知，无页面跳转

                        builder.setSmallIcon(R.mipmap.icon);//设置通知栏消息标题的头像
                        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
                        builder.setTicker("状态栏显示的文字");
                        builder.setContentTitle(list.get(i).getFrom());
                        builder.setAutoCancel(true);//点击后消失


                        String message = list.get(i).getBody().toString().substring(4, list.get(i).getBody().toString().length() - 1);

                        builder.setContentText(message);
                        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
                        PendingIntent intentPend = PendingIntent.getActivity(WDApplication.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(intentPend);
                        NotificationManager manager = (NotificationManager) WDApplication.this.getSystemService(WDApplication.this.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });

    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SharedPreferences getShare() {
        return sharedPreferences;
    }

    /**
     * @return 全局唯一的上下文
     * @author: 康海涛 QQ2541849981
     * @describe: 获取全局Application的上下文
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    private ImagePipelineConfig getConfigureCaches(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        return builder.build();
    }

}