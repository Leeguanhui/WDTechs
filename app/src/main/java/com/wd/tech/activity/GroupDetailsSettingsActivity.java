package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.view.MyDialog;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.UploadGroupHeadPicPresenter;
import com.wd.tech.presenter.UserHeaderPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date:2019/2/20 18:50
 * author:陈国星(陈国星)
 * function:
 */
public class GroupDetailsSettingsActivity extends WDActivity {


    @BindView(R.id.group_details_setting_back)
    ImageView groupDetailsSettingBack;
    @BindView(R.id.group_details_setting_name)
    TextView groupDetailsSettingName;
    @BindView(R.id.group_details_setting_icon)
    SimpleDraweeView groupDetailsSettingIcon;
    @BindView(R.id.group_details_setting_yes)
    Button groupDetailsSettingYes;
    private int flag = 2;//判断是否是群成员 默认不是
    private int userid;
    private String session1d;
    private int groupId;
    private String name;
    private String icon;
    private Dialog dialog;
    private DoTheTaskPresenter doTheTaskPresenter;
    private static int output_X = 100;
    private static int output_Y = 100;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private View contentView;
    private Dialog bottomDialog;
    TextView cancel_btn;
    private LinearLayout btn_take_photo, btn_pick_photo;
    private UserHeaderPresenter userHeaderPresenter;
    private String path;
    private UploadGroupHeadPicPresenter headPicPresenter;
    MyDialog mDialog;
    private View view;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_details_settings;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        headPicPresenter = new UploadGroupHeadPicPresenter(new PIC());
//底部弹出dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        LoginUserInfoBean infoBean = getUserInfo(this);
        if (infoBean != null) {
            session1d = infoBean.getSessionId();
            userid = infoBean.getUserId();
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("groupName");
        groupId = intent.getIntExtra("groupId", 0);
        icon = intent.getStringExtra("groupImage");
        groupDetailsSettingIcon.setImageURI(Uri.parse(icon));
        groupDetailsSettingName.setText(name);
        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        btn_take_photo = contentView.findViewById(R.id.tv_take_photo);
        btn_pick_photo = contentView.findViewById(R.id.tv_take_pic);
        cancel_btn = contentView.findViewById(R.id.tv_cancel);
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                if (!(path == null || path.equals(""))) {
                    headPicPresenter.request(userid,session1d,groupId, path);
                }
                bottomDialog.dismiss();
            }
        });
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                if (!(path == null || path.equals(""))) {
                    headPicPresenter.request(userid,session1d,groupId, path);
                }
                bottomDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

    }

    @Override
    protected void destoryData() {

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
                    Toast.makeText(this, "没有SDCard!", Toast.LENGTH_LONG)
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
        String path = StringUtils.getRealPathFromUri(this, uri);
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
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), photo, null, null));
            path = StringUtils.getRealPathFromUri(this, uri);
            groupDetailsSettingIcon.setImageURI(Uri.parse("file:///" + path));
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

    //Dialog
    private void show(View contentViewss) {
        bottomDialog.setContentView(contentViewss);
        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentViewss.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @OnClick({R.id.group_details_setting_back, R.id.group_details_setting_icon, R.id.group_details_setting_member, R.id.group_details_setting_intro, R.id.group_details_setting_message, R.id.group_details_setting_manage, R.id.group_details_setting_chat, R.id.group_details_setting_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.group_details_setting_back:
                finish();

                break;
            case R.id.group_details_setting_icon:
                show(contentView);
                break;
            case R.id.group_details_setting_member:
                Intent intent1 = new Intent(getApplicationContext(), GroupMembersActivity.class);
                intent1.putExtra("groupId", groupId);
                startActivity(intent1);
                break;
            case R.id.group_details_setting_intro:
                Intent intent2 = new Intent(getApplicationContext(), GroupIntroduceActivity.class);
                intent2.putExtra("groupId", groupId);
                startActivity(intent2);
                break;
            case R.id.group_details_setting_message:
                Intent intent_g = new Intent(GroupDetailsSettingsActivity.this, GroupChatActivity.class);
                startActivity(intent_g);
                break;
            case R.id.group_details_setting_manage:
                Intent intent = new Intent(GroupDetailsSettingsActivity.this, GroupMemberActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                break;
            case R.id.group_details_setting_chat:
                break;
            case R.id.group_details_setting_yes:

                break;
        }
    }




    class WhetherInGroup implements ICoreInfe<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                flag = data.getFlag();

            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class PIC implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(GroupDetailsSettingsActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

