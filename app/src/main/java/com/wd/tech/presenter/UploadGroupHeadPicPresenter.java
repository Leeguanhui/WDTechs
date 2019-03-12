package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadGroupHeadPicPresenter extends BasePresenter {
    public UploadGroupHeadPicPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);
        File file = new File((String) args[3]);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"), file));
        return allUrls.uploadGroupHeadPic((int) args[0],
                (String) args[1],(int)args[2], builder.build());
    }
}
