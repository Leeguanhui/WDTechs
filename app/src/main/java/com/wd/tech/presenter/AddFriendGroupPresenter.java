package com.wd.tech.presenter;



import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;
import io.reactivex.Observable;

/**
 * 用户购买VIP
 */

public class AddFriendGroupPresenter extends BasePresenter {


    public AddFriendGroupPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        return allUrls.addFriendGroup((int)args[0],(String)args[1],(String) args[2]);
    }


}

