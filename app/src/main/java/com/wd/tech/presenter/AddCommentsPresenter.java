package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * 作者：夏洪武
 * 时间：2019/2/26.
 * 邮箱：
 * 说明：
 */
public class AddCommentsPresenter extends BasePresenter {
    public AddCommentsPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.AddComments((int)args[0],(String) args[1],(String)args[2],(String)args[3]);
    }
}