package com.wd.tech.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * 判断网络连接是否已开
 * true 已打开  false 未打开
 *
 */
public class Uris {
    public static boolean isConn(Context context) {
        boolean isFlag = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            isFlag = connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return isFlag;
    }
}

