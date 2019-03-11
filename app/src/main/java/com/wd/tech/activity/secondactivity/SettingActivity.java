package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.activity.thirdlyactivity.SignatureActivity;
import com.wd.tech.activity.thirdlyactivity.UpdatePwdActivity;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.WDApplication;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.face.RegisterActivity;
import com.wd.tech.presenter.ByIdUserInfoPresenter;
import com.wd.tech.presenter.ModifyNickNamePresenter;
import com.wd.tech.presenter.UserHeaderPresenter;
import com.wd.tech.presenter.WheWeChatPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class SettingActivity extends WDActivity implements CustomAdapt {

    private final String TAG = this.getClass().toString();
    private static int output_X = 100;
    private static int output_Y = 100;
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    @BindView(R.id.my_header)
    SimpleDraweeView mHeader;
    @BindView(R.id.my_name)
    EditText mName;
    @BindView(R.id.my_sex)
    TextView mSex;
    @BindView(R.id.my_brith)
    TextView mBrith;
    @BindView(R.id.my_phone)
    TextView mPhone;
    @BindView(R.id.my_mail)
    TextView mMail;
    @BindView(R.id.my_jifen)
    TextView mJifen;
    @BindView(R.id.my_vip)
    TextView mVip;
    @BindView(R.id.my_face)
    TextView mFace;
    //    @BindView(R.id.my_wx)
//    TextView mWx;
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private LinearLayout btn_take_photo, btn_pick_photo;
    TextView cancel_btn;
    private UserHeaderPresenter userHeaderPresenter;
    private LoginUserInfoBean userInfo;
    private Dialog dialog;
    private View contentView;
    private Dialog bottomDialog;
    private IWXAPI mWechatApi;
    static int type = 1;
    //网络数据
    private ByIdUserInfoBean byIdUserInfoBean;
    private ModifyNickNamePresenter modifyNickNamePresenter;
    private WheWeChatPresenter wheWeChatPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        wheWeChatPresenter = new WheWeChatPresenter(new WhetherWeChatResult());
        modifyNickNamePresenter = new ModifyNickNamePresenter(new NickNameResult());
        userHeaderPresenter = new UserHeaderPresenter(new UserHeaderResult());
        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        btn_take_photo = contentView.findViewById(R.id.tv_take_photo);
        btn_pick_photo = contentView.findViewById(R.id.tv_take_pic);
        cancel_btn = contentView.findViewById(R.id.tv_cancel);
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        userInfo = getUserInfo(this);
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        wheWeChatPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        //底部弹出dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                bottomDialog.dismiss();
            }
        });
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                bottomDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        dialog = CircularLoading.showLoadDialog(this, "加载", true);
    }

    @Override
    public void onResume() {
        super.onResume();
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
    }

    @OnClick(R.id.dropout)
    public void dropout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
        alert.setTitle("提示");
        alert.setMessage("是否退出登录");
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserInfo(SettingActivity.this);
                edit.putString("mysign", "");
                edit.commit();
                finish();
            }
        });
        alert.setNegativeButton("取消", null);
        alert.show();
    }

    @OnClick(R.id.my_face)
    public void mFace() {
        //绑定FaceId
        new AlertDialog.Builder(SettingActivity.this)
                .setTitle("请选择注册方式")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(new String[]{"打开图片", "拍摄照片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                ContentValues values = new ContentValues(1);
                                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                ((WDApplication) (SettingActivity.this.getApplicationContext())).setCaptureImage(uri);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                                break;
                            case 0:
                                Intent getImageByalbum = new Intent(Intent.ACTION_GET_CONTENT);
                                getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE);
                                getImageByalbum.setType("image/jpeg");
                                startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP);
                                break;
                            default:
                                ;
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.updatepwd)
    public void updatepwd() {
        startActivity(new Intent(this, UpdatePwdActivity.class));
    }

    @OnClick(R.id.back_bnt)
    public void back_bnt() {
        finish();
    }


//    @OnClick(R.id.my_wx)
//    public void mWeix() {
//        type = 2;
//        mWechatApi = WXAPIFactory.createWXAPI(SettingActivity.this, "wx4c96b6b8da494224", false);
//        mWechatApi.registerApp("wx4c96b6b8da494224");
//        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "wechat_sdk_demo";
//        mWechatApi.sendReq(req);
//        finish();
//    }

    public static int getType() {
        return type;
    }

    public static void setType() {
        type = 1;
    }

    @OnClick(R.id.line1)
    public void line1() {
        startActivity(new Intent(SettingActivity.this, SignatureActivity.class));
    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R.id.my_header)
    public void my_header() {

        show(contentView);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    /**
     * 根据ID查询信息
     */
    private class ByIdUserResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            byIdUserInfoBean = (ByIdUserInfoBean) result.getResult();
            mHeader.setImageURI(Uri.parse(byIdUserInfoBean.getHeadPic()));
            mName.setText(byIdUserInfoBean.getNickName());
            int sex = byIdUserInfoBean.getSex();
            if (sex == 1) {
                mSex.setText("男");
            } else {
                mSex.setText("女");
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date.setTime(byIdUserInfoBean.getBirthday());
            String Datetime = format.format(date);
            mBrith.setText(Datetime);
            mPhone.setText(byIdUserInfoBean.getPhone());
            mMail.setText(byIdUserInfoBean.getEmail());
            mJifen.setText(byIdUserInfoBean.getIntegral() + "");
            if (byIdUserInfoBean.getWhetherVip() == 1) {
                mVip.setText("是");
            } else {
                mVip.setText("否");
            }
            if (byIdUserInfoBean.getWhetherFaceId() == 1) {
                mFace.setText("已绑定");
            } else {
                mFace.setText("未绑定");
            }

            CircularLoading.closeDialog(dialog);
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);

        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));


        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = intent.getData();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0) {
                Log.e(TAG, "error");
            } else {
                Log.i(TAG, "bmp [" + bmp.getWidth() + "," + bmp.getHeight());
            }
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            Log.i(TAG, "RESULT =" + resultCode);
            if (intent == null) {
                return;
            }
            Bundle bundle = intent.getExtras();
            String path = bundle.getString("imagePath");
            Log.i(TAG, "path=" + path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((WDApplication) (SettingActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            startRegister(bmp, file);
        }


        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
//            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
//                if (intent != null) {// 得到图片的全路径
//                    Uri uri = intent.getData();
//                    Haha(uri);
//                }


                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);

                    cropRawPhoto(Uri.fromFile(tempFile));
//                    File file = new File(Uri.fromFile(tempFile));
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("app_user_header", fileNameByTimeStamp, requestFile);

                } else {
                    Toast.makeText(SettingActivity.this, "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        String path = StringUtils.getRealPathFromUri(SettingActivity.this, uri);
        userHeaderPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), path);
        Bitmap map = null;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(LOGO_ZOOM_FILE_PATH));
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更换头像
     */
    private class UserHeaderResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(SettingActivity.this, "" + result.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //Dialog
    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        Window window = bottomDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM); //此处可以设置dialog显示的位置
        window.getDecorView().setPadding(0, 0, 0, 0);
        bottomDialog.show();
    }

    /**
     * 修改名称
     */
    private class NickNameResult implements ICoreInfe {
        @Override
        public void success(Object data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 绑定微信
     */
    private class WhetherWeChatResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            int bindStatus = result.getBindStatus();

            if (bindStatus == 1) {
//                mWx.setText("已绑定");
            } else {
//                mWx.setText("未绑定");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * @param uri
     * @return
     */
    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(this, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(SettingActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }


}
