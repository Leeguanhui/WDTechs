package com.wd.tech.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.bean.InitFriendlist;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.PayResult;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.Pay_Chose_Presenter;
import com.wd.tech.presenter.Pay_Vip_Presenter;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DredgeVip extends WDActivity {


    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.year)
    RadioButton year;
    @BindView(R.id.quarter)
    RadioButton quarter;
    @BindView(R.id.month)
    RadioButton month;
    @BindView(R.id.week)
    RadioButton week;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.chose_wx)
    RadioButton choseWx;
    @BindView(R.id.chose_zfb)
    RadioButton choseZfb;
    @BindView(R.id.ok)
    Button ok;
    private int ONE=0;
    private int TWO=0;
    private int NUM=0;
    private Pay_Vip_Presenter pay_vip_presenter;
    private int userId;
    private String sessionId;
    private Pay_Chose_Presenter pay_chose_presenter;
    private String orderId;
    private String payInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_dredge_vip;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pay_chose_presenter = new Pay_Chose_Presenter(new WXPAY());
        pay_vip_presenter = new Pay_Vip_Presenter(new PayBack());
        LoginUserInfoBean userInfo = getUserInfo(this);
        userId = userInfo.getUserId();
        sessionId = userInfo.getSessionId();
    }
    @OnClick(R.id.year)
    public void OnClickYear(){
        money.setText("0.08");
        ONE=1;
        NUM=1004;
    }
    @OnClick(R.id.quarter)
    public void OnClickQuarter(){
        money.setText("0.05");
        ONE=1;
        NUM=1003;
    }
    @OnClick(R.id.month)
    public void OnClickMonth(){
        money.setText("0.03");
        ONE=1;
        NUM=1002;
    }
    @OnClick(R.id.week)
    public void OnClickWeek(){
        money.setText("0.01");
        ONE=1;
        NUM=1001;
    }
    @OnClick(R.id.chose_wx)
    public void OnClickWx(){
        TWO=1;
    }
    @OnClick(R.id.chose_zfb)
    public void OnClickZfb(){
        TWO=2;
    }
    @OnClick(R.id.ok)
    public void OnClickOk(){
        if (ONE==1&&TWO==1){
            String s1 = String.valueOf(userId);
            String SIGN=s1+NUM+"tech";
            String s = MD5(SIGN);
            pay_vip_presenter.request(userId,sessionId,NUM,s);
        }else if(ONE==1&&TWO==2) {
            String s1 = String.valueOf(userId);
            String SIGN=s1+NUM+"tech";
            String s = MD5(SIGN);
            pay_vip_presenter.request(userId,sessionId,NUM,s);
            //Toast.makeText(this, "支付宝支付正在维修，请更换支付方式", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"请选择",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class PayBack implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
             if (data.getStatus().equals("0000")){
                 orderId = data.getOrderId();
                 pay_chose_presenter.request(userId,sessionId,orderId,TWO);
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class WXPAY implements ICoreInfe<Result<String>> {
        @Override
        public void success(final Result data) {
               if (data.getStatus().equals("0000")){
                   if (TWO==1){
                       IWXAPI api= WXAPIFactory.createWXAPI(DredgeVip.this, "wx4c96b6b8da494224",false);//填写自己的APPID
                       api.registerApp("wx4c96b6b8da494224");//填写自己的APPID，注册本身APP
                       PayReq req = new PayReq();//PayReq就是订单信息对象
//给req对象赋值
                       req.appId = data.getAppId();//APPID
                       req.partnerId = data.getPartnerId();//    商户号
                       req.prepayId = data.getPrepayId();//  预付款ID
                       req.nonceStr = data.getNonceStr();//随机数
                       req.timeStamp = data.getTimeStamp();//时间戳
                       req.packageValue = data.getPackageValue();//固定值Sign=WXPay
                       req.sign = data.getSign();//签名

                       api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
                   }else{
                       Runnable payRunnable = new Runnable() {
                           @Override
                           public void run() {
                               payInfo= (String) data.getResult();
                               // 构造PayTask 对象
                               PayTask alipay = new PayTask(DredgeVip.this);
                               // 调用支付接口，获取支付结果
                               Map<String, String> result = alipay.payV2(payInfo,true);

                               Message msg = new Message();
                               msg.what = SDK_PAY_FLAG;
                               msg.obj = result;
                               mHandler.sendMessage(msg);
                           }
                       };
                       // 必须异步调用
                       Thread payThread = new Thread(payRunnable);
                       payThread.start();
                   }
               }
        }
        @Override
        public void fail(ApiException e) {
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String,String>)msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(DredgeVip.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(DredgeVip.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(DredgeVip.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(DredgeVip.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };

}
