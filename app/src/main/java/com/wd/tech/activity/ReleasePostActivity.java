package com.wd.tech.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wd.tech.R;
import com.wd.tech.activity.adapter.ReleasePostAdapter;
import com.wd.tech.activity.view.FullyGridLayoutManager;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.DoTheTaskPresenter;
import com.wd.tech.presenter.ReleasePostPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class ReleasePostActivity extends WDActivity implements CustomAdapt {

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
//    private View contentView;
//    private Dialog bottomDialog;
//    private LinearLayout photo;
//    private LinearLayout pic;
//    private TextView cancel;
//    private static final int CODE_CAMERA_REQUEST = 0xa1;
//    private static final int CODE_RESULT_REQUEST = 0xa2;
//    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
//    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
//    private Uri imageUri;
//    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
//    private Uri cropImageUri;
//    private static final int OUTPUT_X = 480;
//    private static final int OUTPUT_Y = 480;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private PopupWindow pop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_post;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //数据库
        LoginUserInfoBean userInfo = getUserInfo(this);
        doTheTaskPresenter = new DoTheTaskPresenter(new DoTheReuslt());
        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mImageList.setLayoutManager(manager);
//        mImageList.setLayoutManager(new GridLayoutManager(this, 3));

        addCircleAdapter = new ReleasePostAdapter(this, onAddPicClickListener);
//        addCircleAdapter.setSign(1);
//        addCircleAdapter.add(R.drawable.addpicture);
        addCircleAdapter.setList(selectList);
        addCircleAdapter.setSelectMax(maxSelectNum);
//        mRecyclerView.setAdapter(addCircleAdapter);
        mImageList.setAdapter(addCircleAdapter);
        addCircleAdapter.setOnItemClickListener(new ReleasePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(ReleasePostActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(ReleasePostActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ReleasePostActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ReleasePostActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        //发布帖子
        releasePostPresenter = new ReleasePostPresenter(new ReleasePost());
        //计算输入框数量
        textView.setText(String.valueOf("0/140"));
        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(String.valueOf(s.length()) + "/140");
                if (s.length() >= 140) {
                    Toast.makeText(ReleasePostActivity.this, "上限了，亲", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //底部弹出dialog
//        bottomDialog = new Dialog(this, R.style.BottomDialog);

//        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);

//        addCircleAdapter.setOnClick(new ReleasePostAdapter.OnClick() {
////            @Override
////            public void onClick() {
////                show(contentView);
////            }
////        });

//        photo = contentView.findViewById(R.id.tv_take_photo);
//        pic = contentView.findViewById(R.id.tv_take_pic);
//        cancel = contentView.findViewById(R.id.tv_cancel);
//
//        photo.setOnClickListener(this);
//        pic.setOnClickListener(this);
//        cancel.setOnClickListener(this);
    }

    //取消按钮
    @OnClick(R.id.textView1)
    public void textView1() {
        finish();
    }

    //发布帖子
    @OnClick(R.id.textview2)
    public void textview2() {
        String s = mText.getText().toString();
        List<LocalMedia> list = addCircleAdapter.getList();
        if (s.equals("") | s.equals(null) && list.size() < 1) {
            Toast.makeText(this, "请增加要发布的内容！！！", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Uri> stringList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            LocalMedia localMedia = list.get(i);
            String path = localMedia.getCompressPath();
            Uri uri = Uri.parse(path);
            stringList.add(uri);
        }
        releasePostPresenter.request(userId, sessionId, s, stringList);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
//            switch (requestCode) {
//                case PHOTO:
//                    String filePath = getFilePath(null, requestCode, data);
//                    if (!StringUtils.isEmpty(filePath)) {
//                        addCircleAdapter.add(filePath);
//                        addCircleAdapter.notifyDataSetChanged();
//                        bottomDialog.dismiss();
//                    }
//                    break;
//                //拍照完成回调
//                case CODE_CAMERA_REQUEST:
//                    cropImageUri = Uri.fromFile(fileCropUri);
//                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
//                    break;
//                //裁剪完成回调
//                case CODE_RESULT_REQUEST:
//                    //将图片转换成bitmap
//                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
//                    //将bitmap转换成uri地址
//                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                    //将uri地址转换成string类型地址
//                    String path = PhotoUtils.getRealFilePath(this, uri);
//                    if (!StringUtils.isEmpty(path)) {
//                        addCircleAdapter.add(path);
//                        addCircleAdapter.notifyDataSetChanged();
//                        bottomDialog.dismiss();
//                    }
//                    break;
//                default:
//            }
//        }
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);

//                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    addCircleAdapter.setList(selectList);
                    addCircleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 自动获取相机权限
     */
//    private void autoObtainCameraPermission() {
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                Toast.makeText(this, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
//        } else {//有权限直接调用系统相机拍照
//            if (hasSdcard()) {
//                imageUri = Uri.fromFile(fileUri);
//                //通过FileProvider创建一个content类型的Uri
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    imageUri = FileProvider.getUriForFile(ReleasePostActivity.this, "com.zz.fileprovider", fileUri);
//                }
//                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
//            } else {
//                Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            //调用系统相机申请拍照权限回调
//            case CAMERA_PERMISSIONS_REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (hasSdcard()) {
//                        imageUri = Uri.fromFile(fileUri);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            imageUri = FileProvider.getUriForFile(ReleasePostActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
//                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
//                    } else {
//                        Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//
//                    Toast.makeText(this, "请允许打开相机！！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//
//            }
//            default:
//        }
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            //相机
//            case R.id.tv_take_photo:
////                autoObtainCameraPermission();
//                //拍照
//                PictureSelector.create(ReleasePostActivity.this)
//                        .openCamera(PictureMimeType.ofImage())
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
//                break;
//            //相册
//            case R.id.tv_take_pic:
////                Intent intent = new Intent(
////                        Intent.ACTION_PICK,
////                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                getForegroundActivity().startActivityForResult(intent, PHOTO);
//                //相册
//                PictureSelector.create(ReleasePostActivity.this)
//                        .openGallery(PictureMimeType.ofImage())
//                        .maxSelectNum(maxSelectNum)
//                        .minSelectNum(1)
//                        .imageSpanCount(4)
//                        .selectionMode(PictureConfig.MULTIPLE)
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
//                break;
//            case R.id.tv_cancel:
////                bottomDialog.dismiss();
//                closePopupWindow();
//                break;
//        }
//    }
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

//    //Dialog
//    private void show(View contentViewss) {
//        bottomDialog.setContentView(contentViewss);
//        ViewGroup.LayoutParams layoutParams = contentViewss.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        contentViewss.setLayoutParams(layoutParams);
//        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
//        bottomDialog.setCanceledOnTouchOutside(true);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
//        bottomDialog.show();
//    }

    private ReleasePostAdapter.onAddPicClickListener onAddPicClickListener = new ReleasePostAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {

            //第一种方式，弹出选择和拍照的dialog
//            showPop();

            //第二种方式，直接进入相册，但是 是有拍照得按钮的
            //参数很多，根据需要添加

            PictureSelector.create(ReleasePostActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    //.selectionMedia(selectList)// 是否传入已选图片
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                    //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    .rotateEnabled(false) // 裁剪是否可旋转图片
                    //.scaleEnabled()// 裁剪是否可放大缩小图片
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    private void showPop() {
        View bottomView = View.inflate(ReleasePostActivity.this, R.layout.dialog_content_normal, null);
        LinearLayout mAlbum = bottomView.findViewById(R.id.tv_take_photo);
        LinearLayout mCamera = bottomView.findViewById(R.id.tv_take_pic);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);
//        photo = contentView.findViewById(R.id.tv_take_photo);
//        pic = contentView.findViewById(R.id.tv_take_pic);
//        cancel = contentView.findViewById(R.id.tv_cancel);


        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_animStyle);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_take_pic:
                        //相册
                        PictureSelector.create(ReleasePostActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_take_photo:
                        //拍照
                        PictureSelector.create(ReleasePostActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };
//
        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
//        photo.setOnClickListener(this);
//        pic.setOnClickListener(this);
//        cancel.setOnClickListener(this);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }
}
