package com.wd.tech.core;

import com.wd.tech.core.exception.ApiException;

/**
 * 作者：古祥坤 on 2019/2/18 15:22
 * 邮箱：1724959985@qq.com
 */
public interface ICoreInfe<T> {
    void success(T data);
    void fail(ApiException e);
}
