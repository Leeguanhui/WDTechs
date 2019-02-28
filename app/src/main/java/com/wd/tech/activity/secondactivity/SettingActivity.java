package com.wd.tech.activity.secondactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.thirdlyactivity.SignatureActivity;
import com.wd.tech.activity.view.MyDialog;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.ByIdUserInfoPresenter;
import com.wd.tech.presenter.UserHeaderPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class SettingActivity extends WDActivity implements CustomAdapt {
    private static int output_X = 100;
    private static int output_Y = 100;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    @BindView(R.id.my_header)
    SimpleDraweeView mHeader;
    @BindView(R.id.my_name)
    TextView mName;
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
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private View view;
    private MyDialog myDialog;
    private Button btn_take_photo, btn_pick_photo,cancel_btn;
    private UserHeaderPresenter userHeaderPresenter;
    private LoginUserInfoBean userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        userHeaderPresenter = new UserHeaderPresenter(new UserHeaderResult());
        view = View.inflate(this, R.layout.photo_item, null);
        btn_take_photo = view.findViewById(R.id.btn_take_photo);
        btn_pick_photo = view.findViewById(R.id.btn_pick_photo);
        cancel_btn = view.findViewById(R.id.cancel_btn);
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        userInfo = getUserInfo(this);
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        myDialog = new MyDialog(this, view);
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                myDialog.dismiss();
            }
        });
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                myDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
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

    @OnClick(R.id.back_bnt)
    public void back_bnt() {
        finish();
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
        myDialog.getWindow().setGravity(Gravity.BOTTOM);
        Window window = myDialog.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        myDialog.show();
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
            ByIdUserInfoBean byIdUserInfoBean = (ByIdUserInfoBean) result.getResult();
            mHeader.setImageURI(Uri.parse(byIdUserInfoBean.getHeadPic()));
            mName.setText(byIdUserInfoBean.getNickName());
            mSex.setText(byIdUserInfoBean.getSex());
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
}
