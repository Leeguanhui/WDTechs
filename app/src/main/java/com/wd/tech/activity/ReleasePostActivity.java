package com.wd.tech.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.adapter.ReleasePostAdapter;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.PhotoUtils;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.ReleasePostPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.wd.tech.activity.secondactivity.SettingActivity.hasSdcard;

public class ReleasePostActivity extends WDActivity implements View.OnClickListener ,CustomAdapt {

    @BindView(R.id.id_editor_detail)
    EditText mText;
    @BindView(R.id.id_editor_detail_font_count)
    TextView textView;
    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;

    ReleasePostAdapter addCircleAdapter;
    private ReleasePostPresenter releasePostPresenter;
    private String sessionId;
    private int userId;
    private DoTheTaskPresenter doTheTaskPresenter;
    private View contentView;
    private Dialog bottomDialog;
    private LinearLayout photo;
    private LinearLayout pic;
    private TextView cancel;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private Uri imageUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_post;
    }

    @Override
    protected void initView() {
        //数据库

        LoginUserInfoBean userInfo = getUserInfo(this);
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheReuslt());
        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();

        addCircleAdapter = new ReleasePostAdapter();
        addCircleAdapter.setSign(1);
        addCircleAdapter.add(R.drawable.addpicture);
        mImageList.setLayoutManager(new GridLayoutManager(this, 3));
        mImageList.setAdapter(addCircleAdapter);
        //发布帖子
        releasePostPresenter = new ReleasePostPresenter(new ReleasePost());
        //计算输入框数量
        textView.setText(String.valueOf("0/400"));
        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(String.valueOf(s.length()) + "/400");
                if (s.length() >= 400) {
                    Toast.makeText(ReleasePostActivity.this, "上限了，亲", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //底部弹出dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);

        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);

        addCircleAdapter.setOnClick(new ReleasePostAdapter.OnClick() {
            @Override
            public void onClick() {
                show(contentView);
            }
        });

        photo = contentView.findViewById(R.id.tv_take_photo);
        pic = contentView.findViewById(R.id.tv_take_pic);
        cancel = contentView.findViewById(R.id.tv_cancel);

        photo.setOnClickListener(this);
        pic.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @OnClick(R.id.textview2)
    public void textview2() {
        releasePostPresenter.request(userId, sessionId, mText.getText().toString(), addCircleAdapter.getList());
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            switch (requestCode) {
                case PHOTO:
                    String filePath = getFilePath(null, requestCode, data);
                    if (!StringUtils.isEmpty(filePath)) {
                        addCircleAdapter.add(filePath);
                        addCircleAdapter.notifyDataSetChanged();
                        bottomDialog.dismiss();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
                    }
                    break;
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    String path = PhotoUtils.getPath(this, imageUri);
                    if (!StringUtils.isEmpty(path)) {
                        addCircleAdapter.add(path);
                        addCircleAdapter.notifyDataSetChanged();
                        bottomDialog.dismiss();
                    }
                    break;
            }

//
//            Uri uri = Uri.fromFile(file);
//            Parcelable data1 = data.getParcelableExtra("data");
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    imageUri = FileProvider.getUriForFile(ReleasePostActivity.this, "com.zz.fileprovider", fileUri);
//                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            imageUri = FileProvider.getUriForFile(ReleasePostActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(this, "请允许打开相机！！", Toast.LENGTH_SHORT).show();
                }
                break;


            }
            default:
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //相机
            case R.id.tv_take_photo:
                autoObtainCameraPermission();
                break;
            //相册
            case R.id.tv_take_pic:
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getForegroundActivity().startActivityForResult(intent, PHOTO);
                break;
            case R.id.tv_cancel:
                bottomDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //发布帖子成功接口
    private class ReleasePost implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            doTheTaskPresenter.request(userId, sessionId, 1003);
            finish();
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
        bottomDialog.show();
    }
}
