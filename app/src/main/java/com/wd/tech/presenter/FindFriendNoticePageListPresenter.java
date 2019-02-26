package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;
import com.wd.tech.model.NetworkManager;

import io.reactivex.Observable;

public class FindFriendNoticePageListPresenter extends BasePresenter {

    private int page = 1;

    public FindFriendNoticePageListPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }


    @Override
    public Observable observable(Object... args) {
        boolean isRefresh = (boolean)args[2];
        if (isRefresh){
            page=1;
        }else{
            page++;
        }
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.findFriendNoticePageList((int) args[0],(String)args[1],page,(int)args[3]);
    }
}
