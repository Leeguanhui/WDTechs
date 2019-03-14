package com.wd.tech.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.activity.view.CircularLoading;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.WDApplication;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.DaoUtils;
import com.wd.tech.core.utils.RsaCoder;
import com.wd.tech.core.utils.StringUtils;
import com.wd.tech.face.DetecterActivity;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.FindConversationListDao;
import com.wd.tech.greendao.LoginUserInfoBeanDao;
import com.wd.tech.presenter.LoginUserInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.to_reg)
    TextView mToreg;
    @BindView(R.id.user_phone)
    EditText mUserphone;
    @BindView(R.id.mEd_Pwd_Login)
    EditText mUserPwd;
    private LoginUserInfoPresenter loginUserInfoPresenter;
    private IWXAPI mWechatApi;
    private Dialog dialog;
    private int id;
    private static final int REQUEST_CODE_OP = 7;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        loginUserInfoPresenter = new LoginUserInfoPresenter(new LoginResult());
        //跳转到注册页面
        mToreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });
        //输入框不能空格
        mUserphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    mUserphone.setText(str1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mUserPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    mUserphone.setText(str1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.wechat_btn)
    public void mWechatBtn() {
        mWechatApi = WXAPIFactory.createWXAPI(LoginActivity.this, "wx4c96b6b8da494224", false);
        mWechatApi.registerApp("wx4c96b6b8da494224");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        mWechatApi.sendReq(req);
        finish();
    }

    @OnClick(R.id.face_btn)
    public void mFaceBtn() {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("请选择相机")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(new String[]{"后置相机", "前置相机"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startDetector(which);
                    }
                })
                .show();
    }

    @OnClick(R.id.login_btn)
    public void mLoginBtn() {
        String phone = mUserphone.getText().toString();
        String pwd = mUserPwd.getText().toString();
        if (phone.equals("") || phone == null || pwd.equals("") || pwd == null) {
            Toast.makeText(this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isPhoneLegal(phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String RSAPwd = RsaCoder.encryptByPublicKey(pwd);
            loginUserInfoPresenter.request(phone, RSAPwd);
            dialog = CircularLoading.showLoadDialog(LoginActivity.this, "加载中...", true);
        } catch (Exception e) {
            e.printStackTrace();
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

    private void startDetector(int camera) {
        Intent it = new Intent(LoginActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    /**
     * 登录回调接口
     */
    private class LoginResult implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                LoginUserInfoBean result = (LoginUserInfoBean) data.getResult();
                DaoSession daoSession = DaoMaster.newDevSession(LoginActivity.this, LoginUserInfoBeanDao.TABLENAME);
                LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
                loginUserInfoBeanDao.deleteAll();
                LoginUserInfoBean loginUserInfoBean = (LoginUserInfoBean) data.getResult();
                loginUserInfoBean.setStatu(1);
                loginUserInfoBeanDao.insertOrReplace(loginUserInfoBean);
                FindConversationList findConversationList = new FindConversationList();
                findConversationList.setUserName(result.getUserName().toLowerCase());
                findConversationList.setHeadPic(result.getHeadPic());
                findConversationList.setNickName(result.getNickName());
                findConversationList.setUserId(result.getUserId());
                DaoUtils.getInstance().getConversationDao().insertOrReplaceInTx(findConversationList);

                EMClient.getInstance().login(loginUserInfoBean.getUserName(), loginUserInfoBean.getPwd(), new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        if (id == 1) {
                            finish();
                            return;
                        }
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        finish();

                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                CircularLoading.closeDialog(dialog);

            }else{
                Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
