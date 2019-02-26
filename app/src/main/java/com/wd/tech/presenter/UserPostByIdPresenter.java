package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

public class UserPostByIdPresenter extends BasePresenter{
    public UserPostByIdPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.findUserPostById((int)args[0],(String) args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
