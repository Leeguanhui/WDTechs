package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * Created by zxk
 * on 2019/2/26 10:48
 * QQ:666666
 * Describe:
 */
public class CheckMyFriendPresnter extends BasePresenter {
    public CheckMyFriendPresnter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.checkMyFriend((int)args[0],(String)args[1],(int)args[2]);
    }
}
