package com.wd.tech.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.R;
import com.wd.tech.bean.InitFriendlist;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.WDActivity;
import com.wd.tech.core.exception.ApiException;
import com.wd.tech.presenter.Pay_Chose_Presenter;
import com.wd.tech.presenter.Pay_Vip_Presenter;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dredge_vip;
    }

    @Override
    protected void initView() {
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
        money.setText("99");
        ONE=1;
        NUM=1004;
    }
    @OnClick(R.id.quarter)
    public void OnClickQuarter(){
        money.setText("66");
        ONE=1;
        NUM=1003;
    }
    @OnClick(R.id.month)
    public void OnClickMonth(){
        money.setText("20");
        ONE=1;
        NUM=1002;
    }
    @OnClick(R.id.week)
    public void OnClickWeek(){
        money.setText("6");
        ONE=1;
        NUM=1001;
    }
    @OnClick(R.id.chose_wx)
    public void OnClickWx(){
        TWO=1;
    }
    @OnClick(R.id.zhifubao)
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
        }else if(TWO==2) {
            Toast.makeText(this, "支付宝支付正在维修，请更换支付方式", Toast.LENGTH_LONG).show();
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
                 pay_chose_presenter.request(userId,sessionId,orderId,1);
             }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class WXPAY implements ICoreInfe<Result> {
        @Override
        public void success(Result data) {
               if (data.getStatus().equals("0000")){
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
               }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
