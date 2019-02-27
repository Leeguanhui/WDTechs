package com.wd.tech.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @author happy_movie
 * @date 2019/1/27 11:35
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {



    private IWXAPI api;

    private TextView payResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);
        LogUtils.e("com.bw.movie.wxapi包哈哈哈啊");

        payResult = findViewById(R.id.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wx4c96b6b8da494224");
        api.handleIntent(getIntent(), this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //支付成功后的逻辑
                    result = "微信支付成功";

                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    result = "微信支付失败";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "微信支付取消";
                    break;
                default:
                    result = "微信支付未知异常";
                    break;
            }
            payResult.setText(result);
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

}
