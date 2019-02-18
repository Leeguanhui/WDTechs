package com.wd.tech.presenter;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.wd.tech.bean.Result;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.exception.CustomException;
import com.wd.tech.core.exception.ResponseTransformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：古祥坤 on 2019/2/18 15:23
 * 邮箱：1724959985@qq.com
 */
public abstract class BasePresenter {

    private ICoreInfe dataCall;
    private boolean running;
    private Observable observable;
    public BasePresenter(ICoreInfe dataCall) {
        this.dataCall=dataCall;
    }
    protected  abstract Observable observable(Object... args);

    public void request(Object... args){
        if (running){
            return;
        }
        running = true;
        observable = observable(args);
        observable.compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
//                .subscribeOn(Schedulers.newThread())//将请求调度到子线程上
//                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        running = false;
//                        if (result.getStatus().equals("1001")){
//                            Dialog dialog = new AlertDialog.Builder().setMessage("").set.create().sh;
//                        }else {
                        dataCall.success(result);
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        running = false;
                        // 处理异常
//                        UIUtils.showToastSafe("请求失败");
                        //通过异常工具类封装成自定义的ApiException
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });
    }

    public void cancelRequest() {
        if (observable!=null){
            observable.unsubscribeOn(Schedulers.io());
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void unBind() {
        dataCall = null;
    }
}
