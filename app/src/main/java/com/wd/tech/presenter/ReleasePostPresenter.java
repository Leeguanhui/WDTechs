package com.wd.tech.presenter;

import com.wd.tech.core.AllUrls;
import com.wd.tech.core.ICoreInfe;
import com.wd.tech.core.utils.NotWorkUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReleasePostPresenter extends BasePresenter {
    public ReleasePostPresenter(ICoreInfe dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        AllUrls allUrls = NotWorkUtils.getInstance().create(AllUrls.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("content", (String) args[2]);
        List<Object> list = (List<Object>) args[3];
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File(String.valueOf(list.get(i)));
                builder.addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return allUrls.releasePost((int) args[0], (String) args[1], builder.build());
    }
}
