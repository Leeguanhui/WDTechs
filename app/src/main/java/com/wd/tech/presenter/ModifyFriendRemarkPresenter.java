package com.wd.tech.presenter;


import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * 用户购买VIP
 */

public class ModifyFriendRemarkPresenter extends BasePresenter {


    public ModifyFriendRemarkPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.modifyFriendRemark((int)args[0],(String)args[1],(int) args[2],(String)args[3]);
    }


}

