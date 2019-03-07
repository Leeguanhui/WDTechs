package com.wd.tech.presenter;


import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;
import io.reactivex.Observable;

/**
 * Created by ${LinJiangtao}
 * on 2019/2/21
 */
public class FindGroupMemberListPresenter extends BasePresenter {

    public FindGroupMemberListPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.findGroupMemberList((int)args[0],(String)args[1],(int) args[2]);
    }
}
