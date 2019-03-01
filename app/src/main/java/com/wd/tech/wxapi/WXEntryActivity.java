package com.wd.tech.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.activity.LoginActivity;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.secondactivity.SettingActivity;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.core.utils.Constant;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.LoginUserInfoBeanDao;
import com.wd.tech.presenter.WeChatLoginPresenter;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.handleIntent(this.getIntent(), this);
    }

    //应用请求微信的响应结果将通过onResp回调
    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
            final String code = ((SendAuth.Resp) resp).code;
            try {
                String versionName = getVersionName(WXEntryActivity.this);
                WeChatLoginPresenter weChatLoginPresenter = new WeChatLoginPresenter(new WeChatLoginResult());
                weChatLoginPresenter.request(versionName, code);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            Log.e("LKing", "授权登录失败\n\n自动返回");
            finish();
        }
    }

    @Override
    public void onReq(BaseReq req) {
        //......这里是用来处理接收的请求,暂不做讨论
    }


    /**
     * 登录
     */
    private class WeChatLoginResult implements ICoreInfe<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                DaoSession daoSession = DaoMaster.newDevSession(WXEntryActivity.this, LoginUserInfoBeanDao.TABLENAME);
                LoginUserInfoBeanDao loginUserInfoBeanDao = daoSession.getLoginUserInfoBeanDao();
                loginUserInfoBeanDao.deleteAll();
                LoginUserInfoBean loginUserInfoBean = (LoginUserInfoBean) result.getResult();
                loginUserInfoBean.setStatu(1);
                loginUserInfoBeanDao.insertOrReplace(loginUserInfoBean);
                startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 获取版本号
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }
}
