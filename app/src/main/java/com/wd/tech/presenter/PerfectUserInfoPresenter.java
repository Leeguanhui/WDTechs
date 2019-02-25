package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * 作者：夏洪武
 * 时间：2019/2/18.
 * 邮箱：
 * 说明：
 */
public class PerfectUserInfoPresenter extends BasePresenter {
    public PerfectUserInfoPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.perfectUserInfo((int) args[0], (String) args[1], (String) args[2], (int) args[3], (String) args[4],
                (String) args[5], (String) args[6]);
    }
}
