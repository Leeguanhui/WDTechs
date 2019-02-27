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
public class FindUserTaskListPresenter extends BasePresenter {
    public FindUserTaskListPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.findUserTaskList((int) args[0], (String) args[1]);
    }
}
