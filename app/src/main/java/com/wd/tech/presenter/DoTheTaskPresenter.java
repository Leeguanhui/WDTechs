package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * 作者：古祥坤
 * 时间：2019/2/26.
 * 邮箱：
 * 说明：
 */
public class DoTheTaskPresenter extends BasePresenter {
    public DoTheTaskPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.doTheTask((int) args[0], (String) args[1], (int) args[2]);
    }
}
