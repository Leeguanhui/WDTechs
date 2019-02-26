package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import io.reactivex.Observable;

/**
 * Created by zxk
 * on 2019/2/25 14:07
 * QQ:666666
 * Describe:
 */
public class ApplyAddGroupPresenter extends BasePresenter {
    public ApplyAddGroupPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.applyAddGroup((int) args[0], (String) args[1], (int) args[2],(String)args[3]);
    }
}
