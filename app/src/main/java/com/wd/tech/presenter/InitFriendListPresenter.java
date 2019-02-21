package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * Created by zxk
 * on 2019/2/21 19:38
 * QQ:666666
 * Describe:
 */
public class InitFriendListPresenter extends BasePresenter{

    public InitFriendListPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.initFriendList((int) args[0], (String) args[1]);
    }
}
