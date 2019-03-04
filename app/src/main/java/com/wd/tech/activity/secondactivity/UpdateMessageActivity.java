package com.wd.tech.activity.secondactivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.LoginActivity;
import com.wd.tech.activity.ReleasePostActivity;
import com.wd.tech.activity.thirdlyactivity.SignatureActivity;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.activity.view.MyDialog;
import com.wd.tech.activity.view.PickView;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.ByIdUserInfoPresenter;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.ModifyNickNamePresenter;
import com.wd.tech.presenter.PerfectUserInfoPresenter;
import com.wd.tech.presenter.UserHeaderPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class UpdateMessageActivity extends WDActivity implements CustomAdapt {
    private LoginUserInfoBean userInfo;
    @BindView(R.id.my_name)
    EditText mName;
    @BindView(R.id.my_lin)
    LinearLayout mLin;
    @BindView(R.id.my_sex)
    TextView mSex;
    @BindView(R.id.my_brith)
    TextView mBrith;
    @BindView(R.id.my_mali)
    EditText mMali;
    @BindView(R.id.my_herad)
    SimpleDraweeView mHeader;
    PickView pickView;
    private ByIdUserInfoPresenter byIdUserInfoPresenter;
    MyDialog mDialog;
    private View view;
    private PerfectUserInfoPresenter perfectUserInfoPresenter;
    private SharedPreferences sharedPreferences;
    private int sex;
    @BindView(R.id.line2)
    LinearLayout line2;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userHeaderPresenter = new UserHeaderPresenter(new UserHeaderResult());
        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        btn_take_photo = contentView.findViewById(R.id.tv_take_photo);
        btn_pick_photo = contentView.findViewById(R.id.tv_take_pic);
        cancel_btn = contentView.findViewById(R.id.tv_cancel);
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
        //底部弹出dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheReuslt());
        sharedPreferences = getSharedPreferences("mysign", MODE_PRIVATE);
        perfectUserInfoPresenter = new PerfectUserInfoPresenter(new ModifyNameResult());
        byIdUserInfoPresenter = new ByIdUserInfoPresenter(new ByIdUserResult());
        userInfo = getUserInfo(this);
        byIdUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mName.setText(name);
        view = View.inflate(this, R.layout.sex_itme, null);
        mDialog = new MyDialog(this, view);
        pickView = (PickView) view.findViewById(R.id.pvPickView);
//定义滚动选择器的数据项
        ArrayList<String> grade = new ArrayList<>();
        grade.add("女");
        grade.add("男");
//为滚动选择器设置数据
        pickView.setData(grade);
        pickView.setOnSelectListener(new PickView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Log.i("tag", "选择了" + text);
                mSex.setText(text);
            }
        });
        TextView tv_sexdialog_cancel = view.findViewById(R.id.tv_sexdialog_cancel);
        TextView tv_sexdialog_sure = view.findViewById(R.id.tv_sexdialog_sure);
        tv_sexdialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        tv_sexdialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void destoryData() {

    }
    @OnClick(R.id.back_bnt)
    public void back_bnt(){
        finish();
    }
    @OnClick(R.id.ok_btn)
    public void ok_btn() {
        int sexnum = 1;
        String name = mName.getText().toString();
        String sex = mSex.getText().toString();
        String brith = mBrith.getText().toString();
        String mail = mMali.getText().toString();
        if (sex.equals("男")) {
            sexnum = 1;
        } else {
            sexnum = 2;
        }
        String mysign = sharedPreferences.getString("mysign", "");
        perfectUserInfoPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), name, sexnum, mysign, brith, mail);
        if (!(path == null || path.equals(""))) {
            userHeaderPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), path);
        }
        dialog = CircularLoading.showLoadDialog(UpdateMessageActivity.this, "加载中...", true);
    }

    @OnClick(R.id.my_herad)
    public void my_header() {
        show(contentView);
    }

    @OnClick(R.id.line2)
    public void line2() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                mBrith.setText(formatter.format(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    @OnClick(R.id.line1)
    public void line1() {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    @OnClick(R.id.my_lin)
    public void my_lin() {
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        Window window = mDialog.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        mDialog.show();
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
     * 完善个人资料
     */
    private class ModifyNameResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                doTheTaskPresenter.request(userInfo.getUserId(), userInfo.getSessionId(), 1006);
                finish();
            } else {
                Toast.makeText(UpdateMessageActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 根据ID查询信息
     */
    private class ByIdUserResult implements ICoreInfe {
        @Override
        public void success(Object data) {
            Result result = (Result) data;
            ByIdUserInfoBean byIdUserInfoBean = (ByIdUserInfoBean) result.getResult();
            sex = byIdUserInfoBean.getSex();
            if (sex == 1) {
                mSex.setText("男");
            } else {
                mSex.setText("女");
            }
            Date date = new Date();
            date.setTime(byIdUserInfoBean.getBirthday());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            mBrith.setText(format);
            mMali.setText(byIdUserInfoBean.getEmail());
            mHeader.setImageURI(Uri.parse(byIdUserInfoBean.getHeadPic()));
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 做任务接口
     */
    private class DoTheReuslt implements ICoreInfe {
        @Override
        public void success(Object data) {
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
                    Toast.makeText(UpdateMessageActivity.this, "没有SDCard!", Toast.LENGTH_LONG)
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
        String path = StringUtils.getRealPathFromUri(UpdateMessageActivity.this, uri);
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
            path = StringUtils.getRealPathFromUri(UpdateMessageActivity.this, uri);
            mHeader.setImageURI(Uri.parse("file:///" + path));
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

    private class UserHeaderResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (!result.getStatus().equals("0000")) {
                Toast.makeText(UpdateMessageActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
