package com.wd.tech.presenter;


import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;


public class TransferFriendGroupPresenter extends BasePresenter {

    public TransferFriendGroupPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.transferFriendGroup((int)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}
